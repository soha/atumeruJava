/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jp.tokyo.atumerujava.web;

import javax.validation.Valid;

import jp.tokyo.atumerujava.domain.Event;
import jp.tokyo.atumerujava.service.EventService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping(value="/events")
public class EventController {

	@Autowired
	private EventService eventService;
	
	@RequestMapping(value={"", "/", "/list"}, method=RequestMethod.GET)
	@Transactional(readOnly = true)
    public String index(Model model, Pageable pagable) {
		Page<Event> eventList = eventService.findAll(pagable);
        model.addAttribute("events", eventList);
        return "events/list";
	}

	@RequestMapping(value="/new", method=RequestMethod.GET)
	public String form(Model model) {
		model.addAttribute("event", new Event());
		return "events/create";
	}	

	@RequestMapping(value="/create", method=RequestMethod.POST)
	public String create(@Valid @ModelAttribute("event") Event event, BindingResult result, SessionStatus status) {
		if(result.hasErrors()) {
			//model.addAttribute("event", event);
			return "events/create";
		}
		Event newEvent = eventService.save(event);
		return "redirect:/events/" + newEvent.getId().toString();
	}	

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String show(@PathVariable("id") Integer id, Model model) {
		Event event = eventService.findOne(id);
		model.addAttribute("event", event);
		return "events/show";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("event") Event event, Model model) {
		Event updatedEvent = eventService.save(event);
		model.addAttribute("event", updatedEvent);
		return "redirect:/events/" + updatedEvent.getId();
	}

	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Integer id, Model uiModel) {
		Event event = eventService.findOne(id);
		uiModel.addAttribute("event", event);
		return "events/edit";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("id") Integer id, Model model) {
		eventService.delete(id);
		model.asMap().clear();
		return "redirect:/events/list";
	}

//	String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
//		String enc = httpServletRequest.getCharacterEncoding();
//		if (enc == null) {
//			enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
//		}
//		try {
//			pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
//		}
//		catch (UnsupportedEncodingException uee) {}
//		return pathSegment;
//	}	
}
