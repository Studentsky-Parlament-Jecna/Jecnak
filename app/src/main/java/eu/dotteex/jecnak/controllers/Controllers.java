package eu.dotteex.jecnak.controllers;

import eu.dotteex.jecnak.controllers.types.AbsenceController;
import eu.dotteex.jecnak.controllers.types.GradeController;
import eu.dotteex.jecnak.controllers.types.LateArrivalController;
import eu.dotteex.jecnak.controllers.types.NewsController;
import eu.dotteex.jecnak.controllers.types.ProfileController;
import eu.dotteex.jecnak.controllers.types.RecordController;
import eu.dotteex.jecnak.models.Connect;

public class Controllers{

    private final AbsenceController absenceController;
    private final GradeController gradeController;
    private final LateArrivalController lateArrivalController;
    private final NewsController newsController;
    private final ProfileController profileController;
    private final RecordController recordController;


    public Controllers(Connect connect) {

        absenceController = new AbsenceController(connect);
        gradeController = new GradeController(connect);
        lateArrivalController = new LateArrivalController(connect);
        newsController = new NewsController(connect);
        profileController = new ProfileController(connect);
        recordController = new RecordController(connect);
    }

    public AbsenceController getAbsenceController() {
        return absenceController;
    }

    public GradeController getGradeController() {
        return gradeController;
    }

    public LateArrivalController getLateArrivalController() {
        return lateArrivalController;
    }

    public ProfileController getProfileController() {
        return profileController;
    }

    public RecordController getRecordController() {
        return recordController;
    }

    public NewsController getNewsController() {
        return newsController;
    }
}
