package com.example.demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@RestController
@Controller
public class LogicController {
  @Autowired
   private AdminRepository adminrepo;
   @Autowired
   private CustomerRepository custrepo;

    @GetMapping("/welcome")
    public ModelAndView logic() {
      ModelAndView mView=new ModelAndView();
      mView.setViewName("Welcome");
        return mView;
    }
    @GetMapping("/customer")
    public ModelAndView CustDetails(){
      ModelAndView modelAndView=new ModelAndView();
      modelAndView.setViewName("Customer");
      return modelAndView;
    }
    @GetMapping("/admin")
    public ModelAndView AdminLogin(){
      ModelAndView mView=new ModelAndView();
      mView.setViewName("Admin");
      return mView;
    }
    @GetMapping("/AdminRegistration")
    public ModelAndView AdminReg(HttpSession session,HttpServletResponse respose){
       respose.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
      respose.setHeader("Pragma", "no-cache");
      respose.setHeader("Expires","0");
      ModelAndView mView=new ModelAndView();
     
     
      String sessionName=(String)session.getAttribute("adminame");
      if(sessionName!=null){
        mView.setViewName("AdminR");
      }
      else{
        mView.setViewName("Admin");
      }
      return mView;
    }
    @PostMapping("/addadmin")
    public String AddAdmin(@ModelAttribute Admin admin){
     boolean IfExist= adminrepo.existsByEmail(admin.getEmail());
     boolean IfExist1=adminrepo.existsByUserId(admin.getUserId());
      if(IfExist && IfExist1){
        return "<html><body><h1 align=center>"+admin.getName()+"And"+admin.getEmail()+"-(Admin) Already Registered</h1></br><a href='/admin'>Login</a></body></html>";
      }
      else if (IfExist) {
        Admin userData=adminrepo.findByEmail(admin.getEmail());
        return "<html><body><h1 align=center>"+userData.getEmail()+"-(Admin) Already Registered</h1></br><a href='/admin'>Login</a></body></html>";
      
      }
      else if (IfExist1) {
        Admin userData=adminrepo.findByUserId(admin.getUserId());
        return "<html><body><h1 align=center>"+userData.getUserId()+"-(Admin) Already Registered</h1></br><a href='/admin'>Login</a></body></html>";
      
      }

      else{
        adminrepo.save(admin);
         return "<html><body><h1 align=center>"+admin.getName()+"-(Admin) Has Added..</h1></br><div align='center'></br><a href='/admin'>Login</a></div></body></html>";
      }

      
    }
    @PostMapping("/addcust")
    public String AddCustomer(@ModelAttribute Customers Cust,HttpServletResponse response){
      response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
      response.setHeader("Pragma", "no-cache");
      response.setHeader("Expires","0");
      AllCoreLogic CardVerify=new AllCoreLogic();
      boolean ifuserIdexists=custrepo.existsCustomersByUserId(Cust.getUserId());
      boolean ifCardOk= CardVerify.IsCorrectCardNumber(Cust.getCardNumber());
      
      if(ifCardOk){
        if(ifuserIdexists){
          if(custrepo.existsCustomersByEmail(Cust.getEmail())){
            return "Email Exists already";
          }
          else{
            return "UserId Already Exists";
          }
           
        }
        else if(custrepo.existsByCardNumber(Cust.getCardNumber())){
          return "Card Number Already Exists";
        }

        else{
          custrepo.save(Cust);
        return "<html><body><h1 align='center' style='color:green;'>"+Cust.getName()+"-(Customer) Has Added successfully..</h1></br><a href='/adminmainpage'>Back</a></body></html>";
      
        }

        }
      else{
        return  "<html><body><div align='center'><h1 color='red'>Your Card Number--"+Cust.getCardNumber()+"-- Is not Valid</h1></div></br><a href='/CustomerRegistration'>TryAgain</a></body></html>";
      }



      
    }
    @PostMapping("/AdminLogin")
    public ModelAndView AdminLogin1(@RequestParam("userId") String userId,@RequestParam("password") String password,HttpSession Session){
      ModelAndView mv=new ModelAndView();
      try{
     boolean checkExistance= adminrepo.existsByUserId(userId);
     Admin passwo=adminrepo.findByUserId(userId);
     if(checkExistance==false){
      mv.addObject("valuenotfound", userId);
      mv.addObject("condition", "notexist");
      mv.setViewName("Admin");
      return mv;
   }
   else{
    String pass=passwo.getPassword().trim();
    if(pass.equals(password)){
      mv.setViewName("MainPageAjax");
      mv.addObject("adminname",userId);
      mv.addObject("loginsuccess", "logged");
      Session.setAttribute("adminname", userId);
      Session.setAttribute("password", password);
      
      return mv;
    }
    else{
      mv.setViewName("Admin");
      mv.addObject("wrongpassword", "passworderror");
      return mv;
      }
   }
  }
  catch(Exception e){
    mv.setViewName("exceptionMessage");
    mv.addObject("Exception", e.getLocalizedMessage());
    return mv;
  }

    }
  @GetMapping("/exception")
  public ModelAndView Message(){
    ModelAndView mv=new ModelAndView();
    mv.setViewName("exceptionMessage");
    return mv;
  }



    @GetMapping("/adminmainpage")
    public ModelAndView AdminMainPage(HttpSession session,HttpServletResponse response){
      ModelAndView mView=new ModelAndView();
      response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
      response.setHeader("Pragma", "no-cache");
      response.setHeader("Expires","0");
      String CheckUserSession=(String)session.getAttribute("adminname");

      //  Enumeration<String> attributeNames = session.getAttributeNames();
      //   Map<String, Object> attributes = new HashMap<>();
      //   while (attributeNames.hasMoreElements()) {
      //       String attributeName = attributeNames.nextElement();
      //       attributes.put(attributeName, session.getAttribute(attributeName));
      //   }

      //   // Add attributes to the model to view in Thymeleaf template
      //   mView.addObject("session", attributes);
      //   System.out.println(CheckUserSession+" -------this is the session of user");
      if(CheckUserSession==null ||CheckUserSession.length()==0){
        mView.setViewName("admin");
      }
      else{
        mView.setViewName("MainPageAjax");
      }
      return mView;
    }
   @PostMapping("/deleteCust")
   public ModelAndView  deleteCustomer(@RequestParam("userId") String userId,HttpServletResponse response){
    ModelAndView mv=new ModelAndView(); 
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
      response.setHeader("Pragma", "no-cache");
      response.setHeader("Expires","0");
    mv.addObject("userdelete", "userdeletion");
    boolean IfuserExist=custrepo.existsCustomersByUserId(userId);
    if(IfuserExist==true){
      Customers cust=custrepo.findCustomersByUserId(userId);
      custrepo.delete(cust);
      mv.addObject("passorfail", userId);
      mv.setViewName("MainPageAjax");
      return mv;
    }
    else{
      mv.addObject("passorfail", "useriderror");
      mv.setViewName("MainPageAjax");
      return mv;
    }
   }
   @GetMapping("deletecustomer")
   public ModelAndView DeleteCustData(HttpServletResponse resoponse){
    resoponse.setHeader("Cache-Control","no-cache, no-store, must-revalidate");
    resoponse.setHeader("Pragma", "no-cache");
        resoponse.setHeader("Expires", "0");
    ModelAndView mv=new ModelAndView();
    mv.setViewName("deletecustomer");
    return mv;
   }
   @GetMapping("/CustomerRegistration")
   public ModelAndView AddCustomer(){
    ModelAndView mv=new ModelAndView();
    mv.setViewName("CustomerReg");
    return mv;
   }
   
 @PostMapping("/CardNumber")
 public String GetScore(@RequestParam("cardNumber") String CardNumber){
   AllCoreLogic lg=new AllCoreLogic();
        boolean cardExists=custrepo.existsByCardNumber(Long.parseLong(CardNumber));
        boolean isValidNumber=lg.IsCorrectCardNumber(Long.parseLong(CardNumber));
        if(isValidNumber){
          if(cardExists){
            Customers cust=custrepo.findCustomersByCardNumber(Long.parseLong(CardNumber));
              int score=cust.getCreditScore();
            return "<html><body><span>Your Card Score is--"+score+"</span></br><div align='center'><a href='welcome'>Back</></div></body></html>";
          }
          else{
            return "<html><body><span>The cardNumber-"+CardNumber+" is Not available in our data base.</span></br><div align='center'><a href='welcome'>Back</></div></body></html>";
          }
        }
        else{
          return "<html><body><span>The CardNumber --"+CardNumber+" Is Not Valid</span></br><div align='center'><a href='customer'>TryAgain</></div></body></html>";
        }
  
 }


 @GetMapping("/LogOut")
  public ModelAndView testPage(HttpSession session,HttpServletResponse response){
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Expires", "0");
    session.removeAttribute("adminname");
    session.invalidate();
    ModelAndView mv=new ModelAndView();
    mv.setViewName("admin");
    return mv;
  }
 }


   




