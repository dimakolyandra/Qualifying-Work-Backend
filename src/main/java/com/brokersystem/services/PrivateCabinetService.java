package com.brokersystem.services;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brokersystem.models.BrokerFirm;
import com.brokersystem.models.TraderAccount;
import com.brokersystem.models.TradersOrder;
import com.brokersystem.models.TradersQuestions;
import com.brokersystem.models.TradingContract;
import com.brokersystem.models.UserSystem;
import com.brokersystem.response.ChatDialogResponse;
import com.brokersystem.response.ChatDialogWrapper;
import com.brokersystem.response.ChatsListResponse;
import com.brokersystem.response.ChatsListResponseWrapper;
import com.brokersystem.response.ContractListResponse;
import com.brokersystem.response.DealResponse;
import com.brokersystem.response.DealResponseWrapper;

@Service
public class PrivateCabinetService extends BaseService{

    private final static Logger logger = LoggerFactory.getLogger(Logger.class);
    
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
    
    private List<TradingContract> getOnlyOpened(List<TradingContract> contracts){
    	List<TradingContract> result = new ArrayList<TradingContract>();
    	for(TradingContract contract: contracts){
    		if (contract.getContractStatus().equals("OPEN")){
    			result.add(contract);
    		}
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
        return new ContractListResponse(setContractsData(getOnlyOpened(contracts)));
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
     
     // Here will be request to bank 
     TradingContract contract = tradingContractDAO.getObj(contractId);
     TraderAccount firstAccount = findNeedAccount(contract, sellIso);
     TraderAccount secondAccount = findNeedAccount(contract, buyIso);
     TradersOrder newOrder = createNewOrder(value, contract, firstAccount, secondAccount);   
     
     // Here will be request to bank
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
        return getOrders(contracts, isOpen, startInd, endInd);
    }
    
    @Transactional
    public void addContract(Integer userId, Integer brokerFirmId){
    	UserSystem user = userDAO.getObj(userId);
    	BrokerFirm choosenFirm = brokerFirmDAO.getObj(brokerFirmId);
        UserSystem leastLoadedWorker = getLeastLoadedWorker(choosenFirm);
        TradingContract newContract = new TradingContract();
        newContract.setContractStatus("OPEN");
        newContract.setBroker(leastLoadedWorker);
        newContract.setTrader(user);
        tradingContractDAO.add(newContract);
        List<TraderAccount> generatedAccounts = generateAccounts(newContract);
        traderAccountDAO.addList(generatedAccounts);
        addFirstMessage(newContract);
    }
    
    private boolean hasOpenedDeals(TradingContract contract){
    	for(TradersOrder order: contract.getTradersOrder()){
    		if (order.getStatus().equals("OPEN"))
    			return true;
    	}
    	return false;
    }
    
    @Transactional
    public boolean removeContract(Integer contractId){
    	TradingContract contract = tradingContractDAO.getObj(contractId);
    	if (hasOpenedDeals(contract))
    		return false;
    	contract.setContractStatus("CLOSE");
    	tradingContractDAO.updateObj(contract);
    	return true;
    }
    
    private TradersQuestions getLatestMessage(TradingContract contract){
    	List<TradersQuestions> questions = contract.getTradersQuestions();
    	return questions.get(questions.size() - 1);
    }
    
    private List<ChatsListResponse> getChatsData(List<TradingContract> contracts){
    	List<ChatsListResponse> result = new ArrayList<ChatsListResponse>();
    	for(TradingContract contract: contracts){
    		if (contract.getContractStatus().equals("CLOSE"))
    			continue;
    		ChatsListResponse chatData = new ChatsListResponse();
    		TradersQuestions lastQuestion = getLatestMessage(contract);
    		String title = contract.getBroker().getFirstName() + " " + contract.getBroker().getSecondName();
    		title += "(" + contract.getBroker().getBrokerFirm().getFirmName() + ")";
    		chatData.setAvatar(contract.getBroker().getBrokerFirm().getAvatarUrl());
    		chatData.setId(contract.getTradingContractId());
    		chatData.setDate(lastQuestion.getDateQuestion());
    		chatData.setSubtitle(lastQuestion.getTextQuestion());
    		chatData.setTitle(title);
    		result.add(chatData);
    	}
    	return result;
    }
    
    @Transactional
    public ChatsListResponseWrapper getChatsList(Integer userId){
    	UserSystem user = userDAO.getObj(userId);
    	List<TradingContract> contracts = user.getTradersContract();
    	List<ChatsListResponse> chatsData = getChatsData(contracts);
    	return new ChatsListResponseWrapper(chatsData);
    }
    
    private List<ChatDialogResponse> getDialogContent(TradingContract contract){
    	List<ChatDialogResponse> content = new ArrayList<ChatDialogResponse>();
    	for(TradersQuestions question: contract.getTradersQuestions()){
    		ChatDialogResponse msg = new ChatDialogResponse();
    		msg.setDate(question.getDateQuestion().toString());
    		if (question.getAuthorFlag()==1)
        		msg.setPosition("left");
    		else
    			msg.setPosition("right");
    		msg.setText(question.getTextQuestion());
    		msg.setType("text");
    		msg.setId(question.getTradersQuestionsId());
    		content.add(msg);
    	}
    	Collections.sort(content);
    	return content;
    }
    
    private Map<String, String> getDialogHeader(TradingContract contract){
    	Map<String, String> header = new HashMap<String, String>();
    	String title = contract.getBroker().getFirstName() + " " + contract.getBroker().getSecondName();
    	title += " (" + contract.getBroker().getBrokerFirm().getFirmName() + ")";
    	header.put("avatar", contract.getBroker().getBrokerFirm().getAvatarUrl());
    	header.put("title", title);
    	return header;
    }
    
    @Transactional
    public ChatDialogWrapper getDialog(Integer contractId){
    	TradingContract contract = tradingContractDAO.getObj(contractId);
    	List<ChatDialogResponse> content = getDialogContent(contract);
    	Map<String, String> header = getDialogHeader(contract);
    	return new ChatDialogWrapper(header, content);
    }
    
    @Transactional
    public void addMessage(String text, Integer contractId){
    	TradingContract contract = tradingContractDAO.getObj(contractId);
    	TradersQuestions newQuestion = new TradersQuestions();
    	newQuestion.setAuthorFlag(0);
    	newQuestion.setContract(contract);
    	newQuestion.setDateQuestion(new Date(Calendar.getInstance().getTime().getTime()));
    	newQuestion.setTextQuestion(text);
    	tradersQuestionsDAO.add(newQuestion);
    }
}