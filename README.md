Plugin based on the template generated by the project [idempiere-plugin-scaffold](https://github.com/ingeint/idempiere-plugin-scaffold)

# Synchronization Idempiere-FasteryCaja
This project synchronize idempiere with Fastery Caja

#### Fastery tables
  * Idempiere->Fastery:
    - People
    - Taxcategories
    - Taxes
    - Categories
    - Products
    - Products_value
    - Roles
    - BSCA_Currency
    - BSCA_Postendertype
  
  
  * Fastery->Idempiere:
    - Customers
    - Receipts
    - Payments
    - Tickets
    - TicketLines
    - Closedcash
    - BSCA_paymentinstapago
 
#### Symmetricds Instalation and Setup

- Download [symmetricds 3.88.44](https://sourceforge.net/projects/symmetricds/files/symmetricds/symmetricds-3.8/symmetric-server-3.8.44.zip)
- Unzip the downloaded file, copy and edit the symmetricds-conf/corp.properties file on engines folder. 
- Run the following command 
```bash
bin/sym
```
- For more information [symmetricds doc](https://www.symmetricds.org/doc/3.10/html/user-guide.html#_installation)
- Run script symmetricds-conf/conf.sql on data base idempiere 
 
#### New idempiere windows:	
 - Roles POS
 - User POS
 - Tender Type POS
 

#### Synchronization  Products: 
   - Register o Update Tax Categories
   - Register o Update Tax
   - Register o Update Product Categories
   - Register o Update Product
   - Add Code product
   - Add Organization(s)
   - Register o Update a Price on Sale Price List. 
   
####  Synchronization  Users: 
- Register o Update Roles (new window Roles POS) 
- Register o Update Users by organization ( new window User POS): only for user with condition isUserPOS = 'Y' 

#### Synchronization Currency: 
- Register o Update Currency

#### Synchronization  Tender Type:
- Register o Update tender type (new window Tender Type POS) 

#### Update Multiply Rate Currency:
- Register o Update Conversion Rate POS (new table BSCA_Conversion_Rate) 
