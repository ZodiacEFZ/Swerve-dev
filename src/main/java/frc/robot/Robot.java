// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.libzodiac.ZDashboard;

public class Robot extends TimedRobot implements ZDashboard.Dashboard {
    private RobotContainer robotContainer;

    protected Robot() {
        addPeriodic(() -> robotContainer.chassis.update(), 0.05, 0.02);
    }

    @Override
    public void robotInit() {
        robotContainer = new RobotContainer();
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
    }

    @Override
    public void disabledInit() {
        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void disabledPeriodic() {
    }

    @Override
    public void disabledExit() {
    }

    @Override
    public void autonomousInit() {
        robotContainer.auto.init();
        dashboard().selectedTab("Auto");
    }

    @Override
    public void autonomousPeriodic() {
        robotContainer.auto.schedule();
        CommandScheduler.getInstance().run();
    }

    @Override
    public void autonomousExit() {
    }

    @Override
    public void teleopInit() {
        dashboard().selectedTab("Teleoperated");
    }

    @Override
    public void teleopPeriodic() {
        robotContainer.drive.schedule();
        CommandScheduler.getInstance().run();
    }

    @Override
    public void teleopExit() {
    }

    @Override
    public void testInit() {
        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void testPeriodic() {
        CommandScheduler.getInstance().run();
    }

    @Override
    public void testExit() {
    }
}
