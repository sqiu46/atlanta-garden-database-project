ResultSet rs2 = con.getResult("select BreezecardNum from Conflict");
        String sql = "Select * from Breezecard";

        if ((ownerb || cardnumberb) ||(lowb || (highb|| showb) )) {
            sql += " where ";


            if (owner.getText().length() != 0) {
                sql += " BelongsTo = '" + owner.getText() + "' and ";
            }
            if (cardnumber.getText().length() != 0) {

                sql += " BreezecardNum = '" + cardnumber.getText() + "' and ";
            }
            if (low.getText().length() != 0) {
                sql += "value >='" + low.getText() + "' and ";

            }
            if (high.getText().length()!=0) {
                sql += "value <='" + high.getText() + "' and ";
            }


            if (!showsus.isSelected()) {

                sql += " NOT \n" +
                        "EXISTS (\n" +
                        "SELECT 1 \n" +
                        "FROM Conflict\n" +
                        "WHERE Conflict.BreezecardNum = Breezecard.BreezecardNum\n" +
                        ") and ";
            }
            sql = sql.substring(0, sql.length()-4);
        }



        
# update
String sql = "Update Breezecard SET Value = " + Double.toString(val) + " where BreezecardNum = " + bnum;
# select
ResultSet rs = con.getResult("Select * from USER where Username = '" + user + "' and IsAdmin = 1");
ResultSet rs3 = con.getResult("Select * from USER where Username = '" + user + "'");
System.out.println("Select * from USER where Username = '" + user + "'");
#insert
con.update("Insert into Breezecard(BreezecardNum,BelongsTo) Values('" + strNum + "','" + name + "')");
#delete
System.out.println("Delete from Conflict where BreezecardNum = '" + bnum + "'");




            String sql1 = "(SELECT Name, PassengerIn, PassengerOut, Revenue "
                    + "FROM (SELECT StopID,Name, COUNT(*) AS PassengerIn,Sum(TripFare) AS Revenue "
                    + "FROM Trip JOIN Station ON Trip.StartsAt=Station.StopID "

                    + " GROUP BY StartsAt) AS S "
                    + "NATURAL LEFT OUTER JOIN "
                    + "(SELECT StopID,Name, COUNT(*) AS PassengerOut "
                    + "FROM Trip JOIN Station ON Trip.EndsAt=Station.StopID "
                    + "GROUP BY EndsAt) AS F "
                    + "WHERE Name IS NOT NULL) "
                    + "UNION(" +
                    "SELECT Name, PassengerIn, PassengerOut, Revenue "
                    + "FROM (SELECT StopID,Name, COUNT(*) AS PassengerIn,Sum(TripFare) AS Revenue "
                    + "FROM Trip JOIN Station ON Trip.StartsAt=Station.StopID "

                    + " GROUP BY StartsAt) AS S "
                    + "NATURAL RIGHT OUTER JOIN "
                    + "(SELECT StopID,Name, COUNT(*) AS PassengerOut "
                    + "FROM Trip JOIN Station ON Trip.EndsAt=Station.StopID "
                    + "GROUP BY EndsAt) AS F "
                    + "WHERE Name IS NOT NULL)";
            rs = con.getResult(sql1);