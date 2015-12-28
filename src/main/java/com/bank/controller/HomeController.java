package com.bank.controller;

import com.bank.beans.DefaultValues;
import com.bank.api.DAO;
import com.bank.entities.AccountEntity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    private final DAO dao;
    private final DefaultValues defaultValues;

    @Autowired
    public HomeController(DAO dao, DefaultValues defaultValues) {
        this.dao = dao;
        this.defaultValues = defaultValues;
    }

    @RequestMapping("/")
    public String index(Model model, HttpSession session) {
        String sessionId = session.getId();
        Collection<AccountEntity> listOfAccounts = defaultValuesPlusUserValues(sessionId);
        model.addAttribute("accountList", listOfAccounts);
        return "index";
    }

    private Collection<AccountEntity> defaultValuesPlusUserValues(String sessionId) {
        List<AccountEntity> allAcounts = new ArrayList<>(defaultValues.getDefaultCollection());
        allAcounts.addAll(dao.findAllBySessionUser(sessionId));
        return allAcounts;
    }

}
