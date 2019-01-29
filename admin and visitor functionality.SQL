-- Phase 2 Create Table Statements:
CREATE TABLE User(
	Username varchar(50),
	Email varchar(50) NOT NULL,
	Password char(32) NOT NULL, --because of md5 hash, yours might be different
	UserType enum('ADMIN', 'OWNER', 'VISITOR') NOT NULL,
	PRIMARY KEY(Username),
	UNIQUE(Email)
);

CREATE TABLE Property(
	ID int CHECK (ID < 100,000), 
	Name varchar(255) NOT NULL,
	Size float NOT NULL,
	IsCommercial boolean NOT NULL,
	IsPublic boolean NOT NULL,
	Street varchar(255) NOT NULL,
	City varchar(100) NOT NULL,
	Zip int NOT NULL,
	PropertyType enum('FARM', 'GARDEN', 'ORCHARD') NOT NULL,
	Owner varchar(50) NOT NULL,
	ApprovedBy varchar(50),
	PRIMARY KEY(ID),
	UNIQUE(Name),
	FOREIGN KEY (Owner) REFERENCES User(Username)
		ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY(ApprovedBy) REFERENCES User(Username)
		ON DELETE SET NULL ON UPDATE CASCADE,
);

CREATE TABLE Visit(
	Username varchar(50),
	PropertyID int,
	VisitDate timestamp NOT NULL,
	Rating int NOT NULL CHECK (Rating >= 1 AND Raing <= 5),
	CONSTRAINT Pk_Visit PRIMARY KEY (Username, PropertyID),
	FOREIGN KEY (Username) REFERENCES User(Username) 
		ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (PropertyID) REFERENCES Property(ID) 
		ON DELETE CASCADE ON UPDATE CASCADE,
);

CREATE TABLE FarmItem(
	Name varchar(100),
	IsApproved boolean NOT NULL,
	Type enum('ANIMAL', 'FRUIT', 'FLOWER', 'VEGETABLE', 'NUT') NOT NULL,
	PRIMARY KEY(Name)
);

CREATE TABLE Has(
	PropertyID int,
	ItemName varchar(100),
	CONSTRAINT Pk_Has PRIMARY KEY(PropertyID, ItemName),
	FOREIGN KEY (PropertyID) REFERENCES Property(ID) 
		ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (ItemName) REFERENCES FarmItem(Name) 
		ON DELETE CASCADE ON UPDATE CASCADE
);


# unconfirmed **************
String sql15 = "select Name, Street, City, Zip, Size, PropertyType, IsPublic, IsCommercial, ID, Owner" + 
"from Property" + 
"where ApprovedBy IS NULL";

# unconfirmed
String sql16 = "select Name, Street, City, Zip, Size, PropertyType, IsPublic, IsCommercial, ID, Owner" + 
"from Property" + 
"where" + search by term + "= '" + search term + "' and ApprovedBy IS NULL";

String sql43 = "select Name, Street, City, Zip, Size, Type, IsPublic, IsCommercial" +
"from Property" + 
"where ID =" + selectedID;

# manage selected property ************** (no animal crop)
String sql17 = "update Property SET Name = '" + Name + "', Street = '" + Address + "', City = '" + City + "', Zip = " +
", Size = " + ", IsPublic = '" + Public + "', IsCommercial = '" + Commercial + "', ApprovedBy = '" + Adminuser + "'" +
"where ID =" + selectedID;

String sql18a = "delete from Has where ItemName = '" + animalorcrop + "')";
# add into Has table ******************
String sql18b = "insert into Has values (" + selectedID + ", '" approved type + "')";

String sql41 = "Delete from Property where ID = " + selectedID;

# confirmed approved by not null ***********
String sql19 = "select Name, Street, City, Zip, Size, PropertyType, IsPublic, IsCommercial, ID, Owner, ApprovedBy, avg(select Rating from Visit group by Prpperty.ID)" + 
"from Property" + 
"inner join Visit ON Property.ID = Visit.PropertyID" +
"where ApprovedBy IS NOT NULL";

#confirmed aproved by not null **************
String sql20 = "select Name, Street, City, Zip, Size, PropertyType, IsPublic, IsCommercial, ID, Owner, ApprovedBy, avg(select Rating from Visit group by Prpperty.ID)" + 
"from Property" + 
"inner join Visit ON Property.ID = Visit.PropertyID" +
"where" + search by term + "= '" + search term + "' and ApprovedBy IS NOT NULL";

String sql21 = "select User.Username, User.Email, count(select * from Visit group by Visit.Username)" + 
"from User" + 
"inner join Visit ON User.Username = Visit.Username";

String sql22 = "Delete from User where Username = '" + seleced username + "'";

Srting sql23 = "Delete from Visit where Username = '" + selected username + "'";

String sql24 = "select User.Username, User.Email, count(select * from Visit group by Visit.Username)" + 
"from User" + 
"inner join Visit ON User.Username = Visit.Username" +
"where" + search by term + "= '" + search term + "'";

String sql25 = "select User.Username, User.Email, count(select * from Property group by Owner)" +
"from Property" + 
"inner join User ON Property.Owner = User.Username";

String sql26 = "Delete from User where Username = '" + seleced username + "'";

String sql27 = "select User.Username, Property.Owner, count(select * from Property group by Owner)" +
"from Property" + 
"inner join User ON Property.Owner = User.Username" +
"where" + search by term + "= '" + search term + "'";


String sql28 = "select Name, Type from FarmItem where IsApproved = 'True'";

String sql29 = "Insert into FarmItem Values('" + name + "'True','" + Type + "')";

String sql30 = "select Name, Type from FarmItem where IsApproved = 'True'" +
"where" + search by term + "= '" + search term + "'";

String sql31 = "Delete from FarmItem where Name = '" + seleced name + "'";

String sql32 = "select Name, Type from FarmItem where IsApproved = 'False'";
# compared to farmitem table *******************
String sql33 = "Update FarmItem SET IsApproved = 'True' where Name ='" + selectedName + "' and Type ='" + Type + "'";
# compared both farmitem ***************
String sql34 = "Delete from FarmItem where Name = '" + seleced name + "' and Type ='" + Type + "'";

String sql35 = "select Name, Street, City, Zip, Size, PropertyType, IsPublic, IsCommercial, ID, count(select * from Visit group by Prpperty.ID), avg(select Rating from Visit group by Prpperty.ID)" +
"from Property" +
"inner join Visit ON Property.ID = Visit.PropertyID";

String sql36 = "select Name, Street, City, Zip, Size, PropertyType, IsPublic, IsCommercial, ID, count(select * from Visit group by Prpperty.ID), avg(select Rating from Visit group by Prpperty.ID)" +
"from Property" +
"inner join Visit ON Property.ID = Visit.PropertyID" +
"where" + search by term + "= '" + search term + "'";

String sql37 = "select Property.Name, Owner, Email, count(select * from Visit group by Visit.Username), Street, City, Zip, Size, avg(select Rating from Visit group by Prpperty.ID), Property.Type, IsPublic, IsCommercial, ID, FarmItem.Type, Farm.Name" +
"from Property" +
"inner join Visit ON Property.ID = Visit.PropertyID" +
"inner join User ON User.Username = Property.Owner" +
"where Property.Name = '" + selectedname + "'";

## insert whole tuple to visit ***************
String sql38 = "Insert into Visit Values ('" + selectedname +"'," + ID + "," + VisitDate + "," + Rating + ")";

# Delete one of the visit ********************
String sql39 = "Delete from Visit where Name = '" + selectedname + "'";

String sql40 = "select Property.Name, Date, Rating" +
"from Visit"
"inner join Visit ON Property.ID = Visit.PropertyID";

















