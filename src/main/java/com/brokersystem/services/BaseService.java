package com.brokersystem.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.brokersystem.dao.BaseDAO;
import com.brokersystem.models.BrokerFirm;
import com.brokersystem.models.Currency;
import com.brokersystem.models.TraderAccount;
import com.brokersystem.models.TradersOrder;
import com.brokersystem.models.TradersQuestions;
import com.brokersystem.models.TradingContract;
import com.brokersystem.models.UserSystem;

public class BaseService {
    @Autowired
    @Qualifier("userSystemDao")
    protected BaseDAO<UserSystem, Integer> userDAO;

    @Autowired
    @Qualifier("brokerFirmDao")
    protected BaseDAO<BrokerFirm, Integer> brokerFirmDAO;

    @Autowired
    @Qualifier("tradingContractDao")
    protected BaseDAO<TradingContract, Integer> tradingContractDAO;

    @Autowired
    @Qualifier("currencyDao")
    protected BaseDAO<Currency, String> currencyDAO;
    
    @Autowired
    @Qualifier("traderAccountDao")
    protected BaseDAO<TraderAccount, Integer> traderAccountDAO;
    
    @Autowired
    @Qualifier("tradersOrderDao")
    protected BaseDAO<TradersOrder, Integer> tradersOrderDAO;
    
    @Autowired
    @Qualifier("tradersQuestionsDao")
    protected BaseDAO<TradersQuestions, Integer> tradersQuestionsDAO;
    
//  @Autowired
//  @Qualifier("traderAccountDao")
//  BaseDAO<TraderAccount, Integer> traderAccountDAO;


    
    protected UserSystem getLeastLoadedWorker(BrokerFirm choosenFirm) {
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
    
    protected String generateAccountNumber(){
        char [] accountNumbers = new char[20];
        Random rnd = new Random(System.currentTimeMillis());
        int min = 1, max = 9;
        for(int i = 0; i < 20; i++){
            int num = min + rnd.nextInt(max - min + 1);
            accountNumbers[i] = Character.forDigit(num, 10);
        }
        return new String(accountNumbers);
    }
    
    protected TraderAccount generateAccount(Currency curr, TradingContract contract){
        TraderAccount newAccount = new TraderAccount();
        newAccount.setAccountNumber(generateAccountNumber());
        newAccount.setStatusAccount(0);
        newAccount.setContract(contract);
        newAccount.setAccountCurrency(curr);
        return newAccount;
    }
    
    protected List<TraderAccount> generateAccounts(TradingContract contract){
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

    
}
