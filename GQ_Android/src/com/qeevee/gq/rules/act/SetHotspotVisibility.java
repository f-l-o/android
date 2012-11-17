package com.qeevee.gq.rules.act;

import com.google.android.maps.MapView;

import edu.bonn.mobilegaming.geoquest.GeoQuestApp;
import edu.bonn.mobilegaming.geoquest.HotspotOld;

public class SetHotspotVisibility extends Action {

	@Override
	protected boolean checkInitialization() {
		return params.containsKey("id") && params.containsKey("visible");
	}

	@Override
	public void execute() {
		HotspotOld hotspot = HotspotOld.getExisting(params.get("id"));
		if (hotspot == null) return;
		if (params.get("visible").equals("true")) {
			hotspot.setVisible(true);
		}
		if (params.get("visible").equals("false")) {
			hotspot.setVisible(false);
		}
		
		MapView map = GeoQuestApp.getInstance().getMap();
		if (map != null) {
			map.postInvalidate();
		}
	}

}