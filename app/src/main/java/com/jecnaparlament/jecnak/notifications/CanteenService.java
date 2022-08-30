package com.jecnaparlament.jecnak.notifications;

import android.app.job.JobParameters;
import android.app.job.JobService;

public class CanteenService extends JobService {

    // TODO: implement service
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
