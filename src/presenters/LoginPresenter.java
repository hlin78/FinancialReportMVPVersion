package presenters;

import model.User;
import views.ILoginView;

/**
 * Presenter to handle any logic related to users'
 * login activities.
 * 
 * @version 1.0
 * 
 * @author Team 23
 */
public class LoginPresenter {
	
/**
 * Login activity view.
 */
    private final transient ILoginView view;
    
/**
 * Constructor for login presenter.
 * 
 * @param lview the view
 */
    public LoginPresenter(final ILoginView lview) {
        view = lview;
    }

/**
 * Handle the login button click in the UI,
 * Check if the user's id and password are correct.
 * 
 */
    public void onClick() {
        final String uid = view.getUserid();
        final String password = view.getUserPassword();
        String text = ""; // NOPMD by hailin on 3/29/14 12:59 AM
        final User user = view.findUser(uid); // NOPMD by hailin on 3/29/14 1:01 AM
        if (uid.equals("") || password.equals("")) { // NOPMD by hailin on 3/29/14 1:00 AM
            text = "Username or Password cannot be empty!";
        } else if (user == User.NULL_USER) {
            text = "The userId does not exsit!";
        } else if (user.getPassword().equals(password)) { // NOPMD by hailin on 3/29/14 12:59 AM
            if (user.getUserID().equals("admin")) { // NOPMD by hailin on 3/29/14 1:00 AM
                view.goAdminPage();
            } else {
                view.goUserPage();
            }		
        } else {
            text = "The password is incorrect!";
        }
        view.setResultText(text);
    }
 
///**
// * For user login test.
// * @param user user
// * @param password password
// * @return true if user is correct
// */
//    public boolean login(String user, String password)
//    {
//    	String realUser = "admin";
//    	String realPassword = "123";
//    	
//    	if (user.equals("") || password.equals("")) { // NOPMD by hailin on 3/29/14 1:00 AM
//            return false;
//        } 
//    	if (user.equals(realUser) && !password.equals(realPassword)) {
//            return false;
//    	}
//    	
//        if (!user.equals(realUser) && password.equals(realPassword)) {
//            return false;
//        }
//        if (!user.equals(realUser) && !password.equals(realPassword)) {
//            return false;
//        }
//    	return true;
//    }

/**
 * Handle the forgot password button click in the UI.
 * 
 */
    public void onForgotPasswordClick() {
        view.goForgotPasswordPage();	
    }
}