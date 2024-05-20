import mysql.connector
import pandas as pd

connection= mysql.connector.connect(
  host="localhost",
  user="root",
  password="9*gm68h4x0YDPrRe",
  database="jmadrigalpf"
)

# Tabla Empleados
queryEmployee = "SELECT * FROM EM_EMPLEADOS"# Definimos la consulta SQL

df = pd.read_sql(queryEmployee, connection)# Ejecutamos la consulta y almacenamos los resultados en un DataFrame

df.to_csv('employee.csv', index=False)# Guardamos los datos en un archivo CSV

#Tabla Proyectos
queryProjects="SELECT * FROM PR_PROYECTOS"

df = pd.read_sql(queryProjects, connection)

df.to_csv('projects.csv', index=False)

#Tabla Empleados y Proyectos
queryEMPR="SELECT * FROM PR_EMPLEADOS_PROYECTO"

df = pd.read_sql(queryEMPR, connection)

df.to_csv('employee_projects.csv', index=False)


# Cerrar la conexión
connection.close()