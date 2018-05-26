package com.brokersystem.services;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brokersystem.dao.BaseDAO;
import com.brokersystem.models.BrokerFirm;
import com.brokersystem.models.TraderAccount;
import com.brokersystem.models.TradersOrder;
import com.brokersystem.models.TradingContract;
import com.brokersystem.models.UserSystem;
import com.brokersystem.response.ContractListResponse;
import com.brokersystem.response.DealResponse;
import com.brokersystem.response.DealResponseWrapper;

@Service
public class PrivateCabinetService {

    private final static Logger logger = LoggerFactory.getLogger(Logger.class);
    
    @Autowired
    @Qualifier("brokerFirmDao")
    BaseDAO<BrokerFirm, Integer> brokerFirmDAO;

    @Autowired
    @Qualifier("userSystemDao")
    BaseDAO<UserSystem, Integer> userDAO;
    
    @Autowired
    @Qualifier("tradingContractDao")
    BaseDAO<TradingContract, Integer> tradingContractDAO;
        
//    @Autowired
//    @Qualifier("traderAccountDao")
//    BaseDAO<TraderAccount, Integer> traderAccountDAO;

    @Autowired
    @Qualifier("tradersOrderDao")
    BaseDAO<TradersOrder, Integer> tradersOrderDAO;
    
    private void setNullUsersAndGetFirmNames(List<TradingContract> contracts){
        for(TradingContract contract: contracts)
            contract.getBroker().getBrokerFirm();
    }

    private List<HashMap<String, String>> setContractsData(List<TradingContract> contracts){
        List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
        for(TradingContract contract: contracts){
            HashMap<String, String> forResp = new HashMap<String, String>();
            forResp.put("contractId", contract.getTradingContractId().toString());
            forResp.put("firmName", contract.getBroker().getBrokerFirm().getFirmName());
            result.add(forResp);
        }
        return result;
    }
    
    @Transactional
    public ContractListResponse getDataForNewDeal(Integer userId){
        UserSystem user = userDAO.getObj(userId);
        List<TradingContract> contracts;
        if (user.getUserType() == 1){
            contracts = user.getBrokersContract();
        }
        else{
            contracts = user.getTradersContract();
        }
        setNullUsersAndGetFirmNames(contracts);
        return new ContractListResponse(setContractsData(contracts));
    }
    
    @Transactional
    public List<BrokerFirm> getBrokersList(){
        List<BrokerFirm> firms = brokerFirmDAO.getAllObjects();
        logger.info(firms.toString());
        for(BrokerFirm firm: firms){
            firm.setFirmWorkers(null);
        }
        return firms;
    }
    
    private TradersOrder createNewOrder(
            BigDecimal value, 
            TradingContract contract, 
            TraderAccount firstAccount, 
            TraderAccount secondAccount){
        TradersOrder newOrder = new TradersOrder();
        Date dateBegin = new Date(Calendar.getInstance().getTime().getTime());
        newOrder.setDateBegin(dateBegin);
        newOrder.setStatus("OPEN");
        newOrder.setValue(value);
        newOrder.setSellAccount(firstAccount);
        newOrder.setBuyAccount(secondAccount);
        newOrder.setContract(contract);
        return newOrder;
    }
    
    private TraderAccount findNeedAccount(TradingContract contract, String currIso){
        List<TraderAccount> accounts = contract.getAccounts();
        logger.info(accounts.toString());
        for(TraderAccount account: accounts){
            if (account.getAccountCurrency().getISO().equals(currIso)){
                logger.info(account.getTraderAccountId().toString());
                logger.info(account.getStatusAccount().toString());
                return account;
            }
        }
        return null;
    }
    
    private DealResponseWrapper getOrders(
            List<TradingContract> contracts, 
            boolean isOpen, 
            int startInd, 
            int endInd){
        List<DealResponse> result = new ArrayList<DealResponse>();
        List<TradersOrder> allOrders = new ArrayList<TradersOrder>();
        for(int i = 0; i < contracts.size(); i++){
            List<TradersOrder> orders = contracts.get(i).getTradersOrder();
            for(int j = 0; j < orders.size(); j++){
                if (isOpen && orders.get(j).getStatus().equals("OPEN"))
                    allOrders.add(orders.get(j));
                else if(!isOpen && orders.get(j).getStatus().equals("CLOSE"))
                    allOrders.add(orders.get(j));
            }
        }
        for(int i = 0; i < allOrders.size(); i++){
            if (i >= startInd && i < endInd){
                DealResponse dealInfo = new DealResponse();
                UserSystem brokerWorker = allOrders.get(i).getContract().getBroker();
                BrokerFirm brokerFirm = allOrders.get(i).getContract().getBroker().getBrokerFirm();
                String brokerInfo = brokerWorker.getFirstName() + " " + brokerWorker.getSecondName();
                brokerInfo += "(" + brokerFirm.getFirmName() + ")";
                
                if(!isOpen)
                    dealInfo.setDateClose(allOrders.get(i).getDateEnd().toString());
                
                dealInfo.setId(allOrders.get(i).getTradersOrderId().toString());
                dealInfo.setDateOpen(allOrders.get(i).getDateBegin().toString());
                dealInfo.setValue(allOrders.get(i).getValue().toString());
                dealInfo.setSellAccount(allOrders.get(i).getSellAccount().getAccountNumber());
                dealInfo.setBuyAccount(allOrders.get(i).getBuyAccount().getAccountNumber());
                dealInfo.setSellIso(allOrders.get(i).getSellAccount().getAccountCurrency().getISO());
                dealInfo.setBuyIso(allOrders.get(i).getBuyAccount().getAccountCurrency().getISO());
                dealInfo.setBrokersData(brokerInfo);
                
                result.add(dealInfo);
            }
        }
        return new DealResponseWrapper(result, allOrders.size());
    }
    
    @Transactional
    public void openNewDeal(
            Integer contractId, 
            String sellIso, 
            String buyIso, 
            BigDecimal value){
     
        // Здесь должен быть запрос к серверу брокера, чтобы узнать, достаточно ли средств на счету
     TradingContract contract = tradingContractDAO.getObj(contractId);
     TraderAccount firstAccount = findNeedAccount(contract, sellIso);
     TraderAccount secondAccount = findNeedAccount(contract, buyIso);
     TradersOrder newOrder = createNewOrder(value, contract, firstAccount, secondAccount);   
 
     // Здесь должен быть запрос к серверу брокера, чтобы заблокировать средства
     tradersOrderDAO.add(newOrder);
    }
    
    @Transactional
    public DealResponseWrapper getDeals(Integer userId, int startInd, int endInd, boolean isOpen){
        List<TradingContract> contracts;
        UserSystem user = userDAO.getObj(userId);
        if (user.getUserType() == 1){
            contracts = user.getBrokersContract();
        }
        else{
            contracts = user.getTradersContract();
        }
        logger.info(getOrders(contracts, isOpen, startInd, endInd).toString());
        return getOrders(contracts, isOpen, startInd, endInd);
    }
}