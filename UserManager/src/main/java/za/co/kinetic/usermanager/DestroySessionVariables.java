package za.co.kinetic.usermanager;

import javax.servlet.http.HttpSession;

public class DestroySessionVariables {
    
    private HttpSession session;
    
    public void destroySessionVariables(){
        
        if(session.getAttribute("id") != null && session.getAttribute("token") != null){
            session.removeAttribute("id");
            session.removeAttribute("token");
            session.invalidate();
        }
    }
}
