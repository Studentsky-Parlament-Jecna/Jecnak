package com.jecnaparlament.jecnak.controllers;

import com.jecnaparlament.jecnak.controllers.types.AbsenceController;
import com.jecnaparlament.jecnak.controllers.types.GradeController;
import com.jecnaparlament.jecnak.controllers.types.LateArrivalController;
import com.jecnaparlament.jecnak.controllers.types.NewsController;
import com.jecnaparlament.jecnak.controllers.types.ProfileController;
import com.jecnaparlament.jecnak.controllers.types.RecordController;
import com.jecnaparlament.jecnak.controllers.types.ScheduleController;
import com.jecnaparlament.jecnak.models.Connect;

public class Controllers{

    private final AbsenceController absenceController;
    private final GradeController gradeController;
    private final LateArrivalController lateArrivalController;
    private final NewsController newsController;
    private final ProfileController profileController;
    private final RecordController recordController;
    private final ScheduleController scheduleController;

    public Controllers(Connect connect) {

        absenceController = new AbsenceController(connect);
        gradeController = new GradeController(connect);
        lateArrivalController = new LateArrivalController(connect);
        newsController = new NewsController(connect);
        profileController = new ProfileController(connect);
        recordController = new RecordController(connect);
        scheduleController = new ScheduleController(connect);

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

    public ScheduleController getScheduleController() {
        return scheduleController;
    }
}
