package com.brokersystem.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brokersystem.models.BrokerFirm;
import com.brokersystem.models.TraderAccount;
import com.brokersystem.models.TradingContract;
import com.brokersystem.models.UserSystem;

@Service
public class AuthorizationService extends BaseService{

    private final static Logger logger = LoggerFactory.getLogger(Logger.class);
  

    public int getUsersCountByLogin(String userLogin){
        Map<String, String> userData = new HashMap<String, String>();
        userData.put("login", userLogin);
        return userDAO.getObjectsByFieldsValue(userData).size();
    }
        
    private String encryptPassword(String login, String password) throws NoSuchAlgorithmException{
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        String solt = new String(digest.digest(login.getBytes()));
        String hashedPass = new String(digest.digest(password.getBytes()));
        String passStamp = solt + hashedPass;
        String dataBasePassword = new String(digest.digest(passStamp.getBytes()));
        return dataBasePassword;
    }
    
    @Transactional
    public void registerUser(UserSystem newUser, Integer brokerId) throws NoSuchAlgorithmException{
        BrokerFirm choosenFirm = brokerFirmDAO.getObj(brokerId);
        UserSystem leastLoadedWorker = getLeastLoadedWorker(choosenFirm);
        TradingContract newContract = new TradingContract();
        newContract.setContractStatus("OPEN");
        newContract.setBroker(leastLoadedWorker);
        newContract.setTrader(newUser);
        newUser.setPassword(encryptPassword(newUser.getLogin(), newUser.getPassword()));
        userDAO.add(newUser);
        tradingContractDAO.add(newContract);
        List<TraderAccount> generatedAccounts = generateAccounts(newContract);
        traderAccountDAO.addList(generatedAccounts);
        addFirstMessage(newContract);
    }
    
    @Transactional
    public UserSystem login(String login, String password) throws Exception{
        Map<String, String> userData = new HashMap<String, String>();
        String encrPass = encryptPassword(login, password);
        userData.put("login", login);
        userData.put("password", encrPass);
        UserSystem user = userDAO.getObjectsByFieldsValue(userData).get(0);
        return user; 
    }
    
}
