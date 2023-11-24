import sqlite3 

conn = sqlite3.connect("db.db")
cursor = conn.cursor()

# sql = "CREATE TABLE IF NOT EXISTS elec (id INTEGER PRIMARY KEY AUTOINCREMENT, hp double, hc double, insertedat date);"
# cursor.execute(sql)
# conn.commit()
# sql = "select * from elec"
# cursor.execute(sql)
# res = cursor.fetchall()
# print(res)

sql= "drop table elec;"
cursor.execute(sql)
conn.commit()

