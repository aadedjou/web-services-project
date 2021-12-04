<%@page contentType="text/html;charset=UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<HTML>
<HEAD>
<TITLE>Result</TITLE>
</HEAD>
<BODY>
<H1>Result</H1>

<jsp:useBean id="sampleBankProxyid" scope="session" class="fr.uge.ifservice.bank.BankProxy" />
<%
if (request.getParameter("endpoint") != null && request.getParameter("endpoint").length() > 0)
sampleBankProxyid.setEndpoint(request.getParameter("endpoint"));
%>

<%
String method = request.getParameter("method");
int methodID = 0;
if (method == null) methodID = -1;

if(methodID != -1) methodID = Integer.parseInt(method);
boolean gotMethod = false;

try {
switch (methodID){ 
case 2:
        gotMethod = true;
        java.lang.String getEndpoint2mtemp = sampleBankProxyid.getEndpoint();
if(getEndpoint2mtemp == null){
%>
<%=getEndpoint2mtemp%>
<%
}else{
        String tempResultreturnp3 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(getEndpoint2mtemp));
%>
        <%=tempResultreturnp3%>
        <%
        }
        break;
        case 5:
                gotMethod = true;
                String endpoint_0id=  request.getParameter("endpoint8");
                    java.lang.String endpoint_0idTemp = null;
                if(!endpoint_0id.equals("")){
                 endpoint_0idTemp  = endpoint_0id;
                }
                sampleBankProxyid.setEndpoint(endpoint_0idTemp);
        break;
        case 10:
                gotMethod = true;
                fr.uge.ifservice.bank.Bank getBank10mtemp = sampleBankProxyid.getBank();
        if(getBank10mtemp == null){
        %>
<%=getBank10mtemp %>
<%
}else{
        if(getBank10mtemp!= null){
        String tempreturnp11 = getBank10mtemp.toString();
        %>
        <%=tempreturnp11%>
        <%
        }}
break;
case 13:
        gotMethod = true;
        String pseudo_1id=  request.getParameter("pseudo16");
            java.lang.String pseudo_1idTemp = null;
        if(!pseudo_1id.equals("")){
         pseudo_1idTemp  = pseudo_1id;
        }
        String cash_2id=  request.getParameter("cash18");
        double cash_2idTemp  = Double.parseDouble(cash_2id);
        java.lang.String createAccount13mtemp = sampleBankProxyid.createAccount(pseudo_1idTemp,cash_2idTemp);
if(createAccount13mtemp == null){
%>
<%=createAccount13mtemp %>
<%
}else{
        String tempResultreturnp14 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(createAccount13mtemp));
        %>
        <%= tempResultreturnp14 %>
        <%
}
break;
case 20:
        gotMethod = true;
        String clientName_3id=  request.getParameter("clientName23");
            java.lang.String clientName_3idTemp = null;
        if(!clientName_3id.equals("")){
         clientName_3idTemp  = clientName_3id;
        }
        String amount_4id=  request.getParameter("amount25");
        double amount_4idTemp  = Double.parseDouble(amount_4id);
        sampleBankProxyid.credit(clientName_3idTemp,amount_4idTemp);
break;
case 27:
        gotMethod = true;
        String clientName_5id=  request.getParameter("clientName30");
            java.lang.String clientName_5idTemp = null;
        if(!clientName_5id.equals("")){
         clientName_5idTemp  = clientName_5id;
        }
        String amount_6id=  request.getParameter("amount32");
        double amount_6idTemp  = Double.parseDouble(amount_6id);
        boolean clientCanBuy27mtemp = sampleBankProxyid.clientCanBuy(clientName_5idTemp,amount_6idTemp);
        String tempResultreturnp28 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(clientCanBuy27mtemp));
        %>
        <%= tempResultreturnp28 %>
        <%
break;
case 34:
        gotMethod = true;
        String clientName_7id=  request.getParameter("clientName37");
            java.lang.String clientName_7idTemp = null;
        if(!clientName_7id.equals("")){
         clientName_7idTemp  = clientName_7id;
        }
        String amount_8id=  request.getParameter("amount39");
        double amount_8idTemp  = Double.parseDouble(amount_8id);
        sampleBankProxyid.debit(clientName_7idTemp,amount_8idTemp);
break;
case 41:
        gotMethod = true;
        String clientName_9id=  request.getParameter("clientName44");
            java.lang.String clientName_9idTemp = null;
        if(!clientName_9id.equals("")){
         clientName_9idTemp  = clientName_9id;
        }
        java.lang.String getClientAccountInformation41mtemp = sampleBankProxyid.getClientAccountInformation(clientName_9idTemp);
if(getClientAccountInformation41mtemp == null){
%>
<%=getClientAccountInformation41mtemp %>
<%
}else{
        String tempResultreturnp42 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(getClientAccountInformation41mtemp));
        %>
        <%= tempResultreturnp42 %>
        <%
}
break;
}
} catch (Exception e) { 
%>
Exception: <%= org.eclipse.jst.ws.util.JspUtils.markup(e.toString()) %>
Message: <%= org.eclipse.jst.ws.util.JspUtils.markup(e.getMessage()) %>
<%
return;
}
if(!gotMethod){
%>
result: N/A
<%
}
%>
</BODY>
</HTML>