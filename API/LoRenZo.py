from flask import Flask
import json
import sqlite3
import dataset

app=Flask(__name__)

@app.route("/customer", methods=["GET"])
def get_Customer():
    rows=dataset.getCustomer("SELECT id,name,sex,date,adress,phone,email FROM Customer")
    data=[]
    for r in rows:
        data.append({
            "id":r[0],
            "name":r[1],
            "sex":r[2],
            "date":r[3],
            "adress":r[4],
            "phone":r[5],
            "email":r[6]
        })
    return json.dumps(data)
if __name__ == "__main__":
    app.run()