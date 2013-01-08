package com.qeevee.gq.history;

import android.view.View;
import android.widget.TextView;
import edu.bonn.mobilegaming.geoquest.GeoQuestActivity;
import edu.bonn.mobilegaming.geoquest.GeoQuestApp;
import edu.bonn.mobilegaming.geoquest.mission.MissionActivity;

/**
 * A TransitionItem is inserted into the history list between missions. It
 * visual representation depends on the kinds of missions it connects. These are
 * determined by the variables {@link TransitionItem#from} and
 * {@link TransitionItem#to}.
 * 
 * @author muegge
 * 
 */
public class TransitionItem extends HistoryItem {

    private Class<? extends GeoQuestActivity> to;

    public TransitionItem(GeoQuestActivity predeccessorActivity) {
	super(predeccessorActivity);
	to = MissionActivity.class;
    }

    @Override
    public View getView(View convertView) {
	TextView view = new TextView(GeoQuestApp.getContext());
	view.setText("Transition between: " + activityType.getSimpleName()
		+ " and " + to.getSimpleName() + ".");
	return view;
    }

}