package edu.drexel.goodwin.cpd.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import edu.drexel.goodwin.cpd.domain.Skill;
import edu.drexel.goodwin.cpd.service.SkillManager;

@RooWebScaffold(path = "skill", automaticallyMaintainView = false, formBackingObject = Skill.class)
@RequestMapping("/skill/**")
@Controller
public class SkillController {

	@Autowired
	private SkillManager skillManager;
	
	@RequestMapping(value = "/skill", method = RequestMethod.POST)    
    public String create(@ModelAttribute("skill") Skill skill, BindingResult result, ModelMap modelMap) {    
        if (skill == null) {
        	throw new IllegalArgumentException("A skill is required");        
        }
        
        skillManager.create(skill, result);
        
        if (result.hasErrors()) {        
            modelMap.addAllAttributes(result.getAllErrors());            
            modelMap.addAttribute("skill", skill);            
            return "skill/create";            
        }        
        return "redirect:/skill/" + skill.getId();        
    }

	@RequestMapping(value = "/skill/form", method = RequestMethod.GET)    
    public String createForm(ModelMap modelMap) {    
        modelMap.addAttribute("skill", new Skill());        
        return "skill/create";        
    }

	@RequestMapping(value = "/skill/{id}", method = RequestMethod.GET)    
    public String show(@PathVariable("id") Long id, ModelMap modelMap) {    
        if (id == null) throw new IllegalArgumentException("An Identifier is required");        
        modelMap.addAttribute("skill", Skill.findSkill(id));        
        return "skill/show";        
    }

	@RequestMapping(value = "/skill", method = RequestMethod.GET)    
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ModelMap modelMap) {    
        if (page != null || size != null) {        
            int sizeNo = size == null ? 10 : size.intValue();            
            modelMap.addAttribute("skills", Skill.findSkillEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));            
            float nrOfPages = (float) Skill.countSkills() / sizeNo;            
            modelMap.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));            
        } else {        
            modelMap.addAttribute("skills", Skill.findAllSkills());            
        }        
        return "skill/list";        
    }

	@RequestMapping(method = RequestMethod.PUT)    
    public String update(@ModelAttribute("skill") Skill skill, BindingResult result, ModelMap modelMap) {    
        if (skill == null) throw new IllegalArgumentException("A skill is required");        
        
        skillManager.update(skill, result);
        
        if (result.hasErrors()) {        
            modelMap.addAllAttributes(result.getAllErrors());            
            modelMap.addAttribute("skill", skill);            
            return "skill/update";            
        }        
        return "redirect:/skill/" + skill.getId();        
    }

	@RequestMapping(value = "/skill/{id}/form", method = RequestMethod.GET)    
    public String updateForm(@PathVariable("id") Long id, ModelMap modelMap) {    
        if (id == null) throw new IllegalArgumentException("An Identifier is required");        
        modelMap.addAttribute("skill", Skill.findSkill(id));        
        return "skill/update";        
    }

	@RequestMapping(value = "/skill/{id}", method = RequestMethod.DELETE)    
    public String delete(@PathVariable("id") Long id) {    
        if (id == null) throw new IllegalArgumentException("An Identifier is required");        
        skillManager.delete(id);     
        return "redirect:/skill";        
    }
}
