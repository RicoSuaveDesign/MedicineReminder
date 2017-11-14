Progress Log for medicine reminder to keep track of successes, mistakes, and overall progress in more detail than individual commits.

Ideally updated daily to indicate daily work on project.

10/26/2017 12:32 AM

Officially created repository for project.

Much prep work has been done up to this point, but now the time has come to start writing. Since I do not have all the hardware components yet, work must begin on
the software end. Senior seminar began in early September. September was spent brainstorming and refining ideas. In early October, everyone in class met with the
professor to finalize their project and to begin writeups. Writeups have had their second drafts turned in, and others have started their projects. Also, the
professor is getting ready to turn up the heat on anyone who does not start soon.

Technically, I "have" experience in writing Android apps, but it was for a class and I did not do as well as I could have, so the whole project will be a learning 
experience.

I have set daily reminders (and accidentally two for Wednesdays) to work on thesis, so I will adhere to them, because saying that I hope I will adhere implies that
I might not, so let's just say I will.

Semester goal: Pi is collecting data on all sensors and can send information to mobile app. Mobile app can remind user of something, but this is a notification that
will always occur.

Week goals: 1. Draft how the mobile app will run, including potential class diagrams
2. Get a notification working with a preprogrammed notification time.

11/14/2017

Well.

We have detailed presentations on our projects in two weeks, so a fire has been lit under me to stop taking breaks and take more time to focus.

Switched the notification to an alarm.

I have been doing a lot more thinking on actual implementation of project goals. The RPi will act as a server. It will use Firebase Cloud Messaging and a 
SyncAdapter to update the app immediately of data, though this may end up being only used for sensor data.

Alarm will only be created if user does not take their medicine. At specified times of day, the app will query the database and check if the user has taken their 
meds. If false, create and set off the alarm. 

Potential logic flaw: User takes med out of cabinet after being reminded. Medicine is now set to true for the next day.
Fix: Don't log if it's within an hour after the reminder time.

Still do not have pi 3. Have a pi zero, so I can use that to test pi side things. Only problem is no wifi adapter, so can't really test networking, but can test
basic things like server setup and such. 

Going to actually publish this to Github so I can have actual online source control even if there's not much to show for my work yet.