package com.brokersystem.services;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brokersystem.dao.BaseDAO;
import com.brokersystem.models.BrokerFirm;
import com.brokersystem.models.TradingContract;
import com.brokersystem.models.UserSystem;

@Service
public class AuthorizationService {

    @Autowired
    @Qualifier("userSystemDao")
    BaseDAO<UserSystem, Integer> userDAO;

    @Autowired
    @Qualifier("brokerFirmDao")
    BaseDAO<BrokerFirm, Integer> brokerFirmDAO;

    @Autowired
    @Qualifier("tradingContractDao")
    BaseDAO<TradingContract, Integer> tradingContractDAO;
    
    private final static Logger logger = LoggerFactory.getLogger(Logger.class);

    public int getUsersCountByLogin(String userLogin){
        Map<String, String> userData = new HashMap<String, String>();
        userData.put("login", userLogin);
        return userDAO.getObjectsByFieldsValue(userData).size();
    }

    private UserSystem getLeastLoadedWorker(BrokerFirm choosenFirm) {
        List<UserSystem> workers = choosenFirm.getFirmWorkers();
        Collections.sort(workers, new Comparator<UserSystem>(){
            @Override
            public int compare(UserSystem firstWorker, UserSystem secondWorker){
                int clientCountFirst = firstWorker.getBrokersContract().size();
                int clientCountSecond = secondWorker.getBrokersContract().size();
                return clientCountFirst - clientCountSecond;
            }
        });
        return workers.get(0);
    }
    
    @Transactional
    public void registerUser(UserSystem newUser, Integer brokerId){
        BrokerFirm choosenFirm = brokerFirmDAO.getObj(brokerId);
        UserSystem leastLoadedWorker = getLeastLoadedWorker(choosenFirm);
        TradingContract newContract = new TradingContract();
        newContract.setBroker(leastLoadedWorker);
        newContract.setTrader(newUser);
        userDAO.add(newUser);
        tradingContractDAO.add(newContract);
    }
    
}
