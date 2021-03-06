CREATE TABLE IF NOT EXISTS Click(
  ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  UserID INTEGER,
  Date DATE,
  CampaignID INTEGER NOT NULL,
  Cost FLOAT,
  Impression INTEGER NOT NULL,
  FOREIGN KEY (CampaignID) REFERENCES Campaign(ID) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (Impression) REFERENCES Impression(ID) ON DELETE CASCADE ON UPDATE CASCADE
);
