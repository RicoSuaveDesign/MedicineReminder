# MedicineReminder

Senior project at Carthage College. Intended to be a smart medicine reminder, using both hardware and software to remind user to take medicine.

Hardware is an RPi 3 Model B with an RFID scanner piped through an Arduino Uno R3, and medicine bottles tagged with low frequency RFID tags. Will also include fingerprint scanner, temp/humidity scanner, light sensor.

Software is a mobile app written for Android.

When tagged medicine bottles are scanned, they will be considered 'out' of the cabinet, and no reminder will be triggered during the day so long as user scans bottle again to put bottle 'in' the cabinet again.

Should the user not take their medicine out, they will be reminded to take their medicine. Should they leave their medicine out, they will be reminded to put it away.

As time goes on, all future tense references will be present tense.
