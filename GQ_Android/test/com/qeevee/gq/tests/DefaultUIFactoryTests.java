package com.qeevee.gq.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.reflections.Reflections;

import edu.bonn.mobilegaming.geoquest.mission.MissionActivity;
import edu.bonn.mobilegaming.geoquest.ui.DefaultUIFactory;
import edu.bonn.mobilegaming.geoquest.ui.UIFactory;

@RunWith(GQTestRunner.class)
public class DefaultUIFactoryTests {

    // === TESTS FOLLOW =============================================

    @Test
    public void CheckThatCreatorMethodsForAllUIsAreImplemented() {
	// GIVEN:
	Set<Class<? extends MissionActivity>> missionTypes = collectAllMissionTypes();

	// WHEN:

	// THEN:
	for (Class<? extends MissionActivity> missionTypeUnderTest : missionTypes) {
	    shouldExistCreatorMethodInDefaultUIFactoryFor(missionTypeUnderTest);
	    shouldExistAbstractCreatorMethodInUIFactoryFor(missionTypeUnderTest);
	}
    }

    @Test
    public void generateDefaultUIFactory() {
	assertEquals(DefaultUIFactory.class,
		     UIFactory.getInstance().getClass());
    }

    // === HELPER METHODS FOLLOW =============================================

    final static boolean CONCRETE = true;
    final static boolean ABSTRACT = false;

    private
	    void
	    shouldExistAbstractCreatorMethodInUIFactoryFor(Class<? extends MissionActivity> missionTypeUnderTest) {
	Method[] methods = UIFactory.class.getDeclaredMethods();
	boolean found = false;
	for (int i = 0; i < methods.length
		&& !found; i++) {
	    Method currentMethod = methods[i];
	    found |= creatorMethodFitsFor(missionTypeUnderTest,
					  currentMethod,
					  ABSTRACT);
	}
	if (!found)
	    fail("Abstract method in UIFactory missing for mission type: "
		    + missionTypeUnderTest.getName());
    }

    private static final String MISSION_UI_PACKAGE = "edu.bonn.mobilegaming.geoquest.ui";

    private
	    void
	    shouldExistCreatorMethodInDefaultUIFactoryFor(Class<? extends MissionActivity> missionTypeUnderTest) {
	Method[] methods = DefaultUIFactory.class.getDeclaredMethods();
	boolean found = false;
	for (int i = 0; i < methods.length
		&& !found; i++) {
	    Method currentMethod = methods[i];
	    found |= creatorMethodFitsFor(missionTypeUnderTest,
					  currentMethod,
					  CONCRETE);
	}
    }

    private
	    boolean
	    creatorMethodFitsFor(Class<? extends MissionActivity> missionTypeUnderTest,
				 Method currentMethod,
				 boolean concrete) {
	String missionTypeName = missionTypeUnderTest.getSimpleName();
	Class<?> expectedReturnType;
	Class<?> missionActivityArgumentType;
	try {
	    expectedReturnType = Class.forName(MISSION_UI_PACKAGE
		    + "."
		    + missionTypeName
		    + "UI");
	} catch (ClassNotFoundException e) {
	    fail("UI type "
		    + MISSION_UI_PACKAGE
		    + "."
		    + missionTypeName
		    + "UI"
		    + " missing!");
	    return false;
	}
	try {
	    missionActivityArgumentType = Class.forName(MissionActivity
		    .getPackageBaseName()
		    + missionTypeName);
	} catch (ClassNotFoundException e) {
	    fail("Mission Activity type "
		    + MissionActivity.getPackageBaseName()
		    + "."
		    + missionTypeName
		    + " missing!");
	    return false;
	}
	return nameFits(currentMethod,
			missionTypeName)
		&& returnTypeFits(currentMethod,
				  expectedReturnType)
		&& argumentsFit(currentMethod,
				missionActivityArgumentType)
		&& concretenessFits(concrete,
				    currentMethod);
    }

    private boolean concretenessFits(boolean concrete,
				     Method currentMethod) {
	return concrete != Modifier.isAbstract(currentMethod.getModifiers());
    }

    private boolean argumentsFit(Method currentMethod,
				 Class<?> missionActivityArgumentType) {
	Class<?>[] argumentTypes = currentMethod.getParameterTypes();
	return argumentTypes.length == 1
		&& argumentTypes[0].equals(missionActivityArgumentType);
    }

    private boolean returnTypeFits(Method currentMethod,
				   Class<?> expectedReturnType) {
	return currentMethod.getReturnType().equals(expectedReturnType);
    }

    private boolean nameFits(Method currentMethod, // TODO signature reduction
			     String missionTypeName) {
	return currentMethod.getName().equals("create"
		+ "UI");
    }

    /**
     * @return all concrete classes that are derived of MissionActivity and are
     *         non-deprecated.
     */
    private Set<Class<? extends MissionActivity>> collectAllMissionTypes() {
	Reflections reflections = new Reflections(MissionActivity
		.getPackageBaseName());
	Set<Class<? extends MissionActivity>> missionTypeCandidates = reflections
		.getSubTypesOf(MissionActivity.class);
	Set<Class<? extends MissionActivity>> concreteMissionTypes = new HashSet<Class<? extends MissionActivity>>();
	for (Iterator<Class<? extends MissionActivity>> iterator = missionTypeCandidates
		.iterator(); iterator.hasNext();) {
	    Class<? extends MissionActivity> currentMissionTypeCandidate = (Class<? extends MissionActivity>) iterator
		    .next();
	    if (isConcrete(currentMissionTypeCandidate)
		    && isNonDeprecated(currentMissionTypeCandidate)) {
		concreteMissionTypes.add(currentMissionTypeCandidate);
	    }
	}
	return concreteMissionTypes;
    }

    private
	    boolean
	    isNonDeprecated(Class<? extends MissionActivity> currentMissionTypeCandidate) {
	return !currentMissionTypeCandidate
		.isAnnotationPresent(Deprecated.class);
    }

    private
	    boolean
	    isConcrete(Class<? extends MissionActivity> currentMissionTypeCandidate) {
	return !Modifier.isAbstract(currentMissionTypeCandidate.getModifiers());
    }

}
