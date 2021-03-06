package com.qeevee.gq.tests;

import static com.qeevee.gq.tests.TestUtils.getFieldValue;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;

import android.view.View;

import com.qeevee.ui.ZoomImageView;

import edu.bonn.mobilegaming.geoquest.mission.NPCTalk;
import edu.bonn.mobilegaming.geoquest.ui.NPCTalkUIDefault;

@RunWith(GQTestRunner.class)
public class ZoomImageViewTests {

    NPCTalk npcTalkM;
    NPCTalkUIDefault ui;
    ZoomImageView imageView;

    // === TESTS FOLLOW =============================================

    @Test
    public void correctPathToBitmap() {
	// GIVEN:

	// WHEN:
	startMissionWithCorrectPathToBitmap();

	// THEN:
	shouldShowImage(true);
    }

    @Test
    public void wrongPathToBitmap() {
	// GIVEN:

	// WHEN:
	startMissionWithWrongPathToBitmap();

	// THEN:
	shouldShowImage(false);
    }

    @Test
    public void pathToNonBitmapFile() {
	// GIVEN:

	// WHEN:
	startMissionWithPathToNonBitmapFile();

	// THEN:
	shouldShowImage(false);
    }

    // === HELPER METHODS FOLLOW =============================================

    private void shouldShowImage(boolean shouldShow) {
	if (shouldShow)
	    assertEquals(View.VISIBLE,
			 imageView.getVisibility());
	else
	    assertEquals(View.GONE,
			 imageView.getVisibility());
    }

    private void startMissionWithCorrectPathToBitmap() {
	npcTalkM = (NPCTalk) TestUtils
		.startMissionInGame("ZoomImageViewTest",
				    "NPCTalk",
				    "WithCorrectPathToBitmap");
	ui = (NPCTalkUIDefault) getFieldValue(npcTalkM,
					      "ui");
	imageView = (ZoomImageView) getFieldValue(ui,
						  "charImage");
    }

    private void startMissionWithWrongPathToBitmap() {
	npcTalkM = (NPCTalk) TestUtils
		.startMissionInGame("ZoomImageViewTest",
				    "NPCTalk",
				    "WithWrongPathToBitmap");
	ui = (NPCTalkUIDefault) getFieldValue(npcTalkM,
					      "ui");
	imageView = (ZoomImageView) getFieldValue(ui,
						  "charImage");
    }

    private void startMissionWithPathToNonBitmapFile() {
	npcTalkM = (NPCTalk) TestUtils
		.startMissionInGame("ZoomImageViewTest",
				    "NPCTalk",
				    "WithPathToNonBitmapFile");
	ui = (NPCTalkUIDefault) getFieldValue(npcTalkM,
					      "ui");
	imageView = (ZoomImageView) getFieldValue(ui,
						  "charImage");
    }

}
