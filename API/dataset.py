import sqlite3


def getAll(query):
    conection=sqlite3.connect("LoRenZo.db")
    currsor=conection.execute(query).fetchall()
    conection.close()
    return currsor

if __name__ == "__main__":
    query="SELECT id,name,sex,date,adress,phone,email,userName,pass FROM Customer"
    print(getAll(query))