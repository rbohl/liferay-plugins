/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.sync.engine.documentlibrary.event;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.sync.engine.model.SyncSite;
import com.liferay.sync.engine.service.SyncSiteService;

import java.util.List;
import java.util.Map;

/**
 * @author Shinn Lok
 */
public class GetUserSitesGroupsEvent extends BaseEvent {

	public GetUserSitesGroupsEvent(
		long syncAccountId, Map<String, Object> parameters) {

		super(syncAccountId, _URL_PATH, parameters);
	}

	@Override
	protected void processResponse(String response) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();

		List<SyncSite> syncSites = objectMapper.readValue(
			response, new TypeReference<List<SyncSite>>() {});

		for (SyncSite syncSite : syncSites) {
			syncSite.setSyncAccountId(getSyncAccountId());

			SyncSiteService.update(syncSite);
		}
	}

	private static final String _URL_PATH =
		"/sync-web.syncdlobject/get-user-sites-groups";

}