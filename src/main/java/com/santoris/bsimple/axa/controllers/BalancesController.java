package com.santoris.bsimple.axa.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Lists;
import com.santoris.bsimple.axa.dao.bank.AxaBalanceBankDAO;
import com.santoris.bsimple.axa.model.AxaBalance;

@RequestMapping("/customer/{customerId}/balances")
@Controller
public class BalancesController {
	@Autowired
	private AxaBalanceBankDAO balancesDao;

	@RequestMapping(value = "/show/{accountId}")
	public String getBalances(@PathVariable String customerId, @PathVariable String accountId, Model model) {
		model.addAttribute("customerId", customerId);
		model.addAttribute("accountId", accountId);
		List<AxaBalance> balanceList = balancesDao.getBalanceListForAccount(accountId, customerId);
		model.addAttribute("balances", Lists.reverse(balanceList));
		return "balanceList";
	}
}
