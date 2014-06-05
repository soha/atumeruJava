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

package jp.tokyo.atumerujava.service;

import jp.tokyo.atumerujava.domain.Event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Component("eventService")
@Transactional
class EventServiceImpl implements EventService {

	private final EventRepository eventRepository;

	//private final HotelRepository hotelRepository;

	@Autowired
	//public EventServiceImpl(EventRepository eventRepository, HotelRepository hotelRepository) {
	public EventServiceImpl(EventRepository eventRepository) {
		this.eventRepository = eventRepository;
	}


	@Override
	public Event getEvent(String title, java.util.Date start, java.util.Date end) {
		Assert.notNull(title, "Name must not be null");
		//Assert.notNull(country, "Country must not be null");
		//Assert.isTrue(expression, message);
		return this.eventRepository.findByTitle(title);
	}

}
