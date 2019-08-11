
package com.bigchickenleg.blog.web.admin;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bigchickenleg.blog.po.Tag;
import com.bigchickenleg.blog.service.TagService;
import com.bigchickenleg.blog.vo.BlogQuery;

@Controller
@RequestMapping("admin")
public class TagController {

	@Autowired
	private TagService tagService;
	
	//get the tags page
	@GetMapping("/tags")
	public String tags(@PageableDefault(size=8,sort= {"id"},direction=Sort.Direction.DESC) Pageable pageable,Model model) {
		model.addAttribute("tagPage",tagService.pageTag(pageable));
		List<Tag> tags = tagService.listTag();
		model.addAttribute("tags",tags);
		if(!model.containsAttribute("tag")){
			model.addAttribute("tag",new Tag());
		}
		return "admin/tags";
	}
	


	//add/update tag and verification whether duplicated
	@PostMapping("/tags")
	public String post(@ModelAttribute("tag") @Valid Tag tag, BindingResult result, RedirectAttributes attributes,Model model, 
						@PageableDefault(size=8,sort= {"id"},direction=Sort.Direction.DESC) Pageable pageable) {//validation result
		
		Tag t1 = tagService.getTagByName(tag.getName());
		Long id = tag.getId();
		//form validation
		if(t1 != null && !t1.getId().equals(id)) {
			result.rejectValue("name", "nameError", "Duplicated Tag");
		}
		if(result.hasErrors()) {
//			attributes.addAttribute("org.springframework.validation.BindingResult.tag", result);
//			attributes.addAttribute("tag",tag);
			model.addAttribute("tagPage",tagService.pageTag(pageable));

			return "admin/tags :: tagFragment";
		}
		//check whether input a parentTag.id(autotically new a Tag() to store the parentTag.id)
		Long parentTagId = tag.getParentTag().getId();
		if(parentTagId!=null){
			tag.setParentTag(tagService.getTag(parentTagId));
		}else {
			tag.setParentTag(null);
		}
		// save / update (based on id?=null)
		Tag t;
		if(id==null) {
			t = tagService.saveTag(tag);
		}else {
			t= tagService.update(id, tag);
		}
		if(t==null) {
			attributes.addFlashAttribute("message", "Failure");
		}else {
			attributes.addFlashAttribute("message", "Success");
		}
		model.addAttribute("tagPage",tagService.pageTag(pageable));

		return "admin/tags :: tagFragment";
	}
	
	
	//push the update tag button and get the page
	@GetMapping("/tags/{id}/update")
	public String update(@PageableDefault(size=8,sort= {"id"},direction=Sort.Direction.DESC) Pageable pageable,
							@PathVariable Long id,Model model) {
		
		model.addAttribute("tagPage",tagService.pageTag(pageable));
		List<Tag> tags = tagService.listTag();
		model.addAttribute("tags",tags);
		//get Tag by id
		model.addAttribute("tag", tagService.getTag(id));
		return "admin/tags";
		
	}
	
	//--------------------------tags-ajax-post-load-page------------------------------
	@PostMapping("/tags/page")
	public String loadPage(@PageableDefault(size=8,sort= {"id"},direction=Sort.Direction.DESC) Pageable pageable,
							Model model) {

		model.addAttribute("tagPage", tagService.pageTag(pageable));
		return "admin/tags :: tagFragment";		
		
	}

	
	//delete
	@GetMapping("/tags/{id}/delete")
	public String delete(@PathVariable Long id,RedirectAttributes attributes) {
		Tag tag = tagService.getTag(id);
		if(tag.getChildrenTag().size()==0) {
			tagService.deleteTag(id);
			attributes.addFlashAttribute("message", "Deletion Success");
		}else {
			String tagsName="";
			for(Tag t:tag.getChildrenTag()) {
				tagsName= tagsName+t.getName()+",";
			}
			attributes.addFlashAttribute("message", "This tag has sub tags!"+tagsName);			
		}
		
		return "redirect:/admin/tags";

		
	}
	
}
