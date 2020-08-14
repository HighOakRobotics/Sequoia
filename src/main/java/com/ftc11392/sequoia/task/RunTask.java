package com.ftc11392.sequoia.task;

import com.ftc11392.sequoia.subsystem.Subsystem;

public class RunTask extends Task{
    protected final Runnable m_toRun;

    public RunTask(Runnable toRun, Subsystem requirements){
        m_toRun = toRun;
        addSubsystems(requirements);
    }

    @Override
    public void execute(){m_toRun.run()}
}
