package com.brokersystem.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brokersystem.dao.BaseDAO;
import com.brokersystem.models.BrokerFirm;
import com.brokersystem.models.Currency;
import com.brokersystem.models.TraderAccount;
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

    @Autowired
    @Qualifier("currencyDao")
    BaseDAO<Currency, String> currencyDAO;
    
    @Autowired
    @Qualifier("traderAccountDao")
    BaseDAO<TraderAccount, String> traderAccountDAO;

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
    
    private String generateAccountNumber(){
        char [] accountNumbers = new char[20];
        Random rnd = new Random(System.currentTimeMillis());
        int min = 1, max = 9;
        for(int i = 0; i < 20; i++){
            int num = min + rnd.nextInt(max - min + 1);
            accountNumbers[i] = Character.forDigit(num, 10);
        }
        return new String(accountNumbers);
    }
    
    private TraderAccount generateAccount(Currency curr, TradingContract contract){
        TraderAccount newAccount = new TraderAccount();
        newAccount.setAccountNumber(generateAccountNumber());
        newAccount.setStatusAccount(0);
        newAccount.setContract(contract);
        newAccount.setAccountCurrency(curr);
        return newAccount;
    }
    
    private List<TraderAccount> generateAccounts(TradingContract contract){
        List<TraderAccount> accounts = new ArrayList<TraderAccount>();
        Currency rubl = currencyDAO.getObj("RUB");
        Currency eur = currencyDAO.getObj("EUR");
        Currency usd = currencyDAO.getObj("USD");
        
        TraderAccount rublAccount = generateAccount(rubl, contract);
        TraderAccount eurAccount = generateAccount(eur, contract);
        TraderAccount usdlAccount = generateAccount(usd, contract);
        
        accounts.add(rublAccount);
        accounts.add(eurAccount);
        accounts.add(usdlAccount);
        
        return accounts;
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
        newContract.setBroker(leastLoadedWorker);
        newContract.setTrader(newUser);
        newUser.setPassword(encryptPassword(newUser.getLogin(), newUser.getPassword()));
        userDAO.add(newUser);
        tradingContractDAO.add(newContract);
        List<TraderAccount> generatedAccounts = generateAccounts(newContract);
        traderAccountDAO.addList(generatedAccounts);
    }
    
    @Transactional
    public UserSystem login(String login, String password) throws Exception{
        Map<String, String> userData = new HashMap<String, String>();
        String encrPass = encryptPassword(login, password);
        userData.put("login", login);
        userData.put("password", encrPass);
        List<UserSystem> user = userDAO.getObjectsByFieldsValue(userData);
        return user.get(0); 
    }
    
}
