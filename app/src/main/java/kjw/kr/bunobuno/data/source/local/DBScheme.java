package kjw.kr.bunobuno.data.source.local;

/**
 * Created by kjwook on 2017. 1. 22..
 */

public class DBScheme {

//    public static String createUserInfoTable() {
//        return "create table USER ( PhoneNumber text, Password text, Hint text, PasswordFlag integer, HintItem integer );";
//    }
//
//    public static String createAccountTable() {
//        return "create table ACCOUNT ( Title text, Bank integer, Name text, Number text, PRIMARY KEY (Title) );";
//    }

//    public static String createSiteTable() {
//        return "create table SITE ( Title text, Name text, Password text, PRIMARY KEY (Title) );";
//    }

    public static String createSiteTable() {
        return "create table SITE ( id test, Title text, Password text, PRIMARY KEY (id) );";
    }

//    public static String createCustomTable() {
//        return "create table CUSTOM ( Title text, Name text, Password text, PRIMARY KEY (Title) );";
//    }
//
//    public static String createPhotoTable() {
//        return "create table PHOTO ( Title text, Uri text, PRIMARY KEY (Uri) );";
//    }
//
//    public static String createBankTable() {
//        return "create table BANK ( Bank text , PRIMARY KEY ( Bank ) ); ";
//    }

//        public static String selectUserInfo() {
//            return "select * From USER;";
//        }
//
//        public static String selectAllTitle() {
//            return "select ACCOUNT.Title, SITE.Title, CUSTOM.Title From ACCOUNT, SITE, CUSTOM;";
//        }
//
//        public static String selectAccountTitle() {
//            return "select Title From ACCOUNT;";
//        }
//
//        public static String selectSiteTitle() {
//            return "select Title From SITE;";
//        }
//
//        public static String selectCustomTitle() {
//            return "select Title From CUSTOM;";
//        }
//
//        public static String selectPhotoUri() {
//            return "select Uri From PHOTO;";
//        }
//
//        public static String selectBankList() {
//            return "Select * From BANK;";
//        }
//
//        public static String selectAccountInfo(String title) {
//            String sql = "select * From ACCOUNT where Title = '%s'; ";
//            return String.format(sql, title);
//        }
//
//        public static String selectSiteInfo(String title) throws Exception {
//            String sql = "select * From SITE where Title = '%s'; ";
//            return String.format(sql, Encrypt.encrypt(title));
//        }
//
//        public static String selectCustomInfo(String title) throws Exception {
//            String sql = "select * From CUSTOM where Title = '%s'; ";
//            return String.format(sql, Encrypt.encrypt(title));
//        }
//
//        public static String selectPhotoInfo(String uri) {
//            String sql = "select * From PHOTO where Uri = '%s'; ";
//            return String.format(sql, uri);
//        }
//
//        public static String addUserInfo(UserData data) throws Exception {
//            String sql = "INSERT INTO USER(PhoneNumber, Password, Hint, PasswordFlag, HintItem) VALUES('%s', '%s', '%s', %d, %d);";
//            return String.format(sql, data.phoneNumber, Encrypt.encrypt(data.password), Encrypt.encrypt(data.hint), data.passwordFlag, data.hintItem);
//        }
//
//        public static String addAccountInfo(NumberData data) {
//            String sql = "INSERT INTO ACCOUNT(Title, Bank, Name, Number) VALUES('%s', %d, '%s', '%s');";
//            return String.format(sql, data.title, data.bank, data.name, data.number);
//        }
//
//        public static String addSiteInfo(NumberData data) throws Exception {
//            String sql = "INSERT INTO SITE(Title, Name, Password) VALUES('%s', '%s', '%s');";
//            return String.format(sql, Encrypt.encrypt(data.title), Encrypt.encrypt(data.name), Encrypt.encrypt(data.number));
//        }
//
//        public static String addCustomInfo(NumberData data) throws Exception {
//            String sql = "INSERT INTO CUSTOM(Title, Name, Password) VALUES('%s', '%s', '%s');";
//            return String.format(sql, Encrypt.encrypt(data.title), Encrypt.encrypt(data.name), Encrypt.encrypt(data.number));
//        }
//
//        public static String addPhotoInfo(NumberData data) {
//            String sql = "INSERT INTO PHOTO( Uri ) VALUES('%s');";
//            return String.format(sql, data.uri);
//        }
//
//        public static String addPasswordHint(NumberData data) throws Exception {
//            String sql = "INSERT INTO USER( Hint, HintItem ) VALUES('%s', %d);";
//            return String.format(sql, Encrypt.encrypt(data.hint), data.hintItem);
//        }
//
//        public static String addBankItem (String bank) {
//            String sql = "Insert into BANK( Bank ) values('%s');";
//            return String.format(sql, bank);
//        }
//
//        public static String updateGCMRegId(String key) throws Exception {
//            String sql = "UPDATE USER SET GCMRegId = '%s';";
//            return String.format(sql, Encrypt.encrypt(key));
//        }
//
//        public static String updateAccountInfo(NumberData data) {
//            String sql = "UPDATE ACCOUNT SET Title='%s', Bank=%d, Name='%s', Number='%s' where Title='%s'; ";
//            return String.format( sql, data.title, data.bank, data.name, data.number, data.originalTitle);
//        }
//
//        public static String updateSiteInfo(NumberData data) throws Exception {
//            String sql = "UPDATE SITE SET Title='%s', Name='%s', Password='%s' where Title='%s'; ";
//            return String.format( sql, Encrypt.encrypt(data.title), Encrypt.encrypt(data.name), Encrypt.encrypt(data.number), Encrypt.encrypt(data.originalTitle));
//        }
//
//        public static String updateCustomInfo(NumberData data) throws Exception {
//            String sql = "UPDATE CUSTOM SET Title='%s', Name='%s', Password='%s' where Title='%s'; ";
//            return String.format( sql, Encrypt.encrypt(data.title), Encrypt.encrypt(data.name), Encrypt.encrypt(data.number), Encrypt.encrypt(data.originalTitle));
//        }
//
//        public static String updatePasswordFlag(UserData data) {
//            String url = "UPDATE USER SET PasswordFlag = %d;";
//            return String.format(url, data.passwordFlag);
//        }
//
//        public static String updatePassword(UserData data) throws Exception {
//            String url = "UPDATE USER SET Password = '%s';";
//            return String.format(url, Encrypt.encrypt(data.password));
//        }
//
//        public static String updatePasswordHint(NumberData data) throws Exception {
//            String url = "UPDATE USER SET Hint = '%s', HintItem = %d;";
//            return String.format(url, Encrypt.encrypt(data.hint), data.hintItem);
//        }
//
//        public static String deleteUserData() {
//            return "DELETE FROM USER;";
//        }
//
//        public static String deleteAccountData() {
//            return "DELETE FROM ACCOUNT;";
//        }
//
//        public static String deleteSiteData() {
//            return "DELETE FROM SITE;";
//        }
//
//        public static String deleteCustomData() {
//            return "DELETE FROM CUSTOM;";
//        }
//
//        public static String deletePhotoData() {
//            return "DELETE FROM PHOTO;";
//        }
//
//        public static String deleteAccontInfo(NumberData data) {
//            String sql = "DELETE FROM ACCOUNT WHERE Title = '%s'; ";
//            return String.format( sql, data.originalTitle );
//        }
//
//        public static String deleteSiteInfo(NumberData data) throws Exception {
//            String sql = "DELETE FROM SITE WHERE Title = '%s'; ";
//            Log.i("DBScheme", Encrypt.encrypt(data.originalTitle));
//            return String.format( sql, Encrypt.encrypt(data.originalTitle) );
//        }
//
//        public static String deleteCustomInfo(NumberData data) throws Exception {
//            String sql = "DELETE FROM CUSTOM WHERE Title = '%s'; ";
//            return String.format( sql, Encrypt.encrypt(data.originalTitle) );
//        }
//
//        public static String deletePhotoInfo(NumberData data) {
//            String sql = "DELETE FROM PHOTO WHERE Uri = '%s'; ";
//            return String.format( sql, data.uri );
//        }

}
