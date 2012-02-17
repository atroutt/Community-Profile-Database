package edu.drexel.goodwin.cpd.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import edu.drexel.goodwin.cpd.domain.Interest;
import edu.drexel.goodwin.cpd.service.InterestManager;

@RooWebScaffold(path = "interest", automaticallyMaintainView = false, formBackingObject = Interest.class)
@RequestMapping("/interest/**")
@Controller
public class InterestController {

	@Autowired
	private InterestManager interestManager; 
	
	@RequestMapping(value = "/interest", method = RequestMethod.POST)    
    public String create(@ModelAttribute("interest") Interest interest, BindingResult result, ModelMap modelMap) {    
        if (interest == null) {
        	throw new IllegalArgumentException("An interest is required");        
        }
        
        interestManager.create(interest, result);    
        
        if (result.hasErrors()) {        
            modelMap.addAllAttributes(result.getAllErrors());            
            modelMap.addAttribute("interest", interest);            
            return "interest/create";            
        }        
        return "redirect:/interest/" + interest.getId();        
    }

	@RequestMapping(value = "/interest/form", method = RequestMethod.GET)    
    public String createForm(ModelMap modelMap) {    
        modelMap.addAttribute("interest", new Interest());        
        return "interest/create";        
    }

	@RequestMapping(value = "/interest/{id}", method = RequestMethod.GET)    
    public String show(@PathVariable("id") Long id, ModelMap modelMap) {    
        if (id == null) {
        	throw new IllegalArgumentException("An Identifier is required");        
        }
        modelMap.addAttribute("interest", Interest.findInterest(id));        
        return "interest/show";        
    }

	@RequestMapping(value = "/interest", method = RequestMethod.GET)    
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ModelMap modelMap) {    
        if (page != null || size != null) {        
            int sizeNo = size == null ? 10 : size.intValue();            
            modelMap.addAttribute("interests", Interest.findInterestEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));            
            float nrOfPages = (float) Interest.countInterests() / sizeNo;            
            modelMap.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));            
        } else {        
            modelMap.addAttribute("interests", Interest.findAllInterests());            
        }        
        return "interest/list";        
    }

	@RequestMapping(method = RequestMethod.PUT)    
    public String update(@ModelAttribute("interest") Interest interest, BindingResult result, ModelMap modelMap) {    
        if (interest == null) {
        	throw new IllegalArgumentException("A interest is required");        
        }
        
        interestManager.update(interest, result);       
        
        if (result.hasErrors()) {        
            modelMap.addAllAttributes(result.getAllErrors());            
            modelMap.addAttribute("interest", interest);            
            return "interest/update";            
        }        
        return "redirect:/interest/" + interest.getId();        
    }

	@RequestMapping(value = "/interest/{id}/form", method = RequestMethod.GET)    
    public String updateForm(@PathVariable("id") Long id, ModelMap modelMap) {    
        if (id == null) {
        	throw new IllegalArgumentException("An Identifier is required");        
        }
        modelMap.addAttribute("interest", Interest.findInterest(id));        
        return "interest/update";        
    }

	@RequestMapping(value = "/interest/{id}", method = RequestMethod.DELETE)    
    public String delete(@PathVariable("id") Long id) {    
        if (id == null) {
        	throw new IllegalArgumentException("An Identifier is required");        
        }
        
        interestManager.delete(id);      
        
        return "redirect:/interest";        
    }
}
