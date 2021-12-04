<%@page contentType="text/html;charset=UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<HTML>
<HEAD>
<TITLE>Result</TITLE>
</HEAD>
<BODY>
<H1>Result</H1>

<jsp:useBean id="sampleBankProxyid" scope="session" class="bank.BankProxy" />
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
<%=getEndpoint2mtemp %>
<%
}else{
        String tempResultreturnp3 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(getEndpoint2mtemp));
        %>
        <%= tempResultreturnp3 %>
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
        bank.Bank getBank10mtemp = sampleBankProxyid.getBank();
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
        String clientName_1id=  request.getParameter("clientName16");
            java.lang.String clientName_1idTemp = null;
        if(!clientName_1id.equals("")){
         clientName_1idTemp  = clientName_1id;
        }
        String amount_2id=  request.getParameter("amount18");
        double amount_2idTemp  = Double.parseDouble(amount_2id);
        sampleBankProxyid.credit(clientName_1idTemp,amount_2idTemp);
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
        sampleBankProxyid.debit(clientName_3idTemp,amount_4idTemp);
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
        java.lang.String getClientAccountInformation34mtemp = sampleBankProxyid.getClientAccountInformation(clientName_7idTemp);
if(getClientAccountInformation34mtemp == null){
%>
<%=getClientAccountInformation34mtemp %>
<%
}else{
        String tempResultreturnp35 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(getClientAccountInformation34mtemp));
        %>
        <%= tempResultreturnp35 %>
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