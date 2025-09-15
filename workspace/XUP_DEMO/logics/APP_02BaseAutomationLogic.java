import com.nexacro.java.xapi.data.ColumnHeader;
import com.nexacro.java.xapi.data.DataSet;
import com.nexacro.java.xapi.data.datatype.DataType;
import com.nexacro.java.xapi.data.datatype.MashupDataType;
import com.nexacro.java.xapi.data.datatype.PlatformDataType;
import com.nexacro.java.xapi.data.datatype.SqlDataType;
import com.nexacro.xup.Parameter;
import com.nexacro.xup.ParameterSet;
import com.nexacro.xup.bundle.datasource.DataSourceEvent;
import com.nexacro.xup.bundle.datasource.db.DbDataSourceEvent;
import com.nexacro.xup.bundle.datasource.odata.ODataDataSourceEvent;
import com.nexacro.xup.bundle.datasource.openapi.OpenApiDataSourceEvent;
import com.nexacro.xup.bundle.datasource.sap.SapRfcDataSourceEvent;
import com.nexacro.xup.bundle.datasource.ws.WSDataSourceEvent;
import com.nexacro.xup.bundle.invoker.EventHandler;
import com.nexacro.xup.bundle.invoker.FlowEvent;
import com.nexacro.xup.bundle.invoker.InvokingInfo;
import com.nexacro.xup.bundle.invoker.ResultNameSet;
import com.nexacro.xup.bundle.invoker.RowEvent;
import com.nexacro.xup.bundle.invoker.as400.AS400FlowEvent;
import com.nexacro.xup.bundle.invoker.as400.AS400InvokingInfo;
import com.nexacro.xup.bundle.invoker.dbmodify.DbModifyFlowEvent;
import com.nexacro.xup.bundle.invoker.dbmodify.DbModifyInvokingInfo;
import com.nexacro.xup.bundle.invoker.dbprocedure.DbProcedureFlowEvent;
import com.nexacro.xup.bundle.invoker.dbprocedure.DbProcedureInvokingInfo;
import com.nexacro.xup.bundle.invoker.dbselect.DbSelectFlowEvent;
import com.nexacro.xup.bundle.invoker.dbselect.DbSelectInvokingInfo;
import com.nexacro.xup.bundle.invoker.merge.MergeFlowEvent;
import com.nexacro.xup.bundle.invoker.merge.MergeInvokingInfo;
import com.nexacro.xup.bundle.invoker.model.ModelFlowEvent;
import com.nexacro.xup.bundle.invoker.model.ModelInvokingInfo;
import com.nexacro.xup.bundle.invoker.odata.ODataFlowEvent;
import com.nexacro.xup.bundle.invoker.odata.ODataInvokingInfo;
import com.nexacro.xup.bundle.invoker.odata.ODataInvokingRequest;
import com.nexacro.xup.bundle.invoker.openapi.OpenApiFlowEvent;
import com.nexacro.xup.bundle.invoker.openapi.OpenApiInvokingInfo;
import com.nexacro.xup.bundle.invoker.qmerge.QMergeDataSetHashInfo;
import com.nexacro.xup.bundle.invoker.qmerge.QMergeDataSetSortInfo;
import com.nexacro.xup.bundle.invoker.qmerge.QMergeFlowEvent;
import com.nexacro.xup.bundle.invoker.qmerge.QMergeInvokingInfo;
import com.nexacro.xup.bundle.invoker.saprfc.SapRfcFlowEvent;
import com.nexacro.xup.bundle.invoker.saprfc.SapRfcInvokingInfo;
import com.nexacro.xup.bundle.invoker.ws.WSFlowEvent;
import com.nexacro.xup.bundle.invoker.ws.WSInvokingInfo;
import com.nexacro.xup.bundle.invoker.xmlparser.XmlParserInvokingInfo;
import com.nexacro.xup.bundle.invoker.xup.XupInvokingInfo;
import com.nexacro.xup.bundle.invoker.xupclient.XupFlowEvent;
import com.nexacro.xup.component.automation.AutomationFailException;
import com.nexacro.xup.component.automation.InvokeSkip;
import com.nexacro.xup.component.automation.RowSkip;
import com.nexacro.xup.component.gatherer.ParsedRawData;
import com.nexacro.xup.component.gatherer.RawData;
import com.nexacro.xup.data.MashupFile;
import com.nexacro.xup.data.MashupHeader;
import com.nexacro.xup.data.MashupParameter;
import com.nexacro.xup.model.BaseAutomationLogic;
import com.nexacro.xup.model.automation.InvokingErrorInfo;
import com.nexacro.xup.model.automation.SqlTypeBindingInfo;
import com.nexacro.xup.model.automation.TypeBindingInfo;
import com.nexacro.xup.model.datasource.DataSource;
import com.nexacro.xup.odata.HttpMethod;
import com.nexacro.xup.util.condition.Condition;
import com.nexacro.xup.util.condition.dbModify.DataSetRowProcessConditionInfoSet;
import com.nexacro.xup.util.condition.xmlParser.XmlParserConditionInfo;
import com.nexacro.xup.util.condition.xmlParser.XmlParserConditionInfoSet;
import com.nexacro.xup.util.data.ParsingFailException;
import com.nexacro.xup.util.data.XmlParser;
import com.nexacro.xup.util.data.XmlParsingInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class APP_02BaseAutomationLogic extends BaseAutomationLogic {

	public ParameterSet execute(ParameterSet inputParameterSet) throws AutomationFailException{
		ParameterSet outputParameterSet = new ParameterSet();
		ParameterSet globalParameterSet = new ParameterSet();
		globalParameterSet.add(inputParameterSet);
		initParameterSet(globalParameterSet);
		start(globalParameterSet);
		
		user(globalParameterSet);

		if(globalParameterSet.getParameter("dsPending") != null){
			outputParameterSet.add(globalParameterSet.getParameter("dsPending"));
		}
		
		end(globalParameterSet, outputParameterSet);
		return outputParameterSet;
	}
	private void initParameterSet(ParameterSet globalParameterSet) {
		DataSet dsPending = new DataSet("dsPending");

		dsPending.addColumn("EV_ID", PlatformDataType.STRING, 255);
		dsPending.addColumn("APV_ID", PlatformDataType.STRING, 255);
		dsPending.addColumn("YEAR", PlatformDataType.STRING, 255);
		dsPending.addColumn("MONTH", PlatformDataType.STRING, 255);
		dsPending.addColumn("EV_TITLE", PlatformDataType.STRING, 255);
		dsPending.addColumn("EV_DESC", PlatformDataType.STRING, 255);
		dsPending.addColumn("SITE_ID", PlatformDataType.STRING, 255);
		dsPending.addColumn("SITE_NM", PlatformDataType.STRING, 255);
		dsPending.addColumn("DEPT_ID", PlatformDataType.STRING, 255);
		dsPending.addColumn("DEPT_NM", PlatformDataType.STRING, 255);
		dsPending.addColumn("REG_EMP_NO", PlatformDataType.STRING, 255);
		dsPending.addColumn("REG_USER_NM", PlatformDataType.STRING, 255);
		dsPending.addColumn("APV_STATUS_CD", PlatformDataType.STRING, 255);

		globalParameterSet.add(dsPending);
	}
	public void start(ParameterSet globalParameterSet)throws AutomationFailException {//!findOffset_start!
	}
	public void end(ParameterSet globalParameterSet, ParameterSet outputParameterSet)throws AutomationFailException {//!findOffset_end!
	}
	public void user(ParameterSet globalParameterSet) throws AutomationFailException {//!findOffset_user!
		ParameterSet tmpParameterSet = new ParameterSet();
		tmpParameterSet.add(globalParameterSet.getParameter("dsCond"));

		ResultNameSet resultNameSet = new ResultNameSet();
		resultNameSet.add("RESULT0", "dsPending", null);

		DbSelectInvokingInfo info = new DbSelectInvokingInfo();
		info.setDomainName("XUP_DEMO");
		info.setDataSourceName("NEXACRO_DEMO");
		info.setSqlSelect("SELECT\n E.EV_ID AS EV_ID,								/*문서 ID*/\n A.APV_ID AS APV_ID,								/*결재 ID*/\n EXTRACT(YEAR FROM E.REG_DT) AS YEAR,			/*등록 연도*/\n EXTRACT(MONTH FROM E.REG_DT) AS MONTH,			/*등록 월*/\n E.EV_TITLE AS EV_TITLE,							/*문서명*/\n E.EV_DESC AS EV_DESC,							/*등록 코멘트*/\n E.SITE_ID AS SITE_ID,							/*등록사업장 ID*\n E.SITE_NM AS SITE_NM,							/*등록사업장*/\n E.DEPT_NM AS DEPT_NM,							/*등록부서*/\n E.DEPT_ID AS DEPT_ID,							/*등록부서 ID*/\n E.REG_EMP_NO AS REG_EMP_NO,						/*등록자 사번*/\n E.REG_USER_NM AS REG_USER_NM,					/*등록자*/\n A.APV_STATUS_CD AS APV_STATUS_CD				/*결재상태*/\n FROM EVD_DOC E, APV_DOC A\n WHERE EXTRACT(YEAR FROM E.REG_DT) = #dsCond.YEAR#\n AND E.EV_ID IS NOT NULL\n AND A.APV_ID IS NOT NULL\n AND E.EV_ID = A.EV_ID\n AND A.APV_STATUS_CD = 'PENDING'\n \n <isNotEmpty property=\"dsCond.MONTH\">\n AND EXTRACT(MONTH FROM E.REG_DT) = #dsCond.MONTH#\n </isNotEmpty>\n \n <isNotEmpty property=\"dsCond.TITLE\">\n AND E.EV_TITLE LIKE '%'||#dsCond.TITLE# ||'%'\n </isNotEmpty>\n \n <isNotEmpty property=\"dsCond.SITE_NM\">\n AND E.SITE_NM LIKE '%'|| #dsCond.SITE_NM# ||'%'\n </isNotEmpty>\n \n <isNotEmpty property=\"dsCond.DEPT_NM\">\n AND E.DEPT_NM LIKE '%'|| #dsCond.DEPT_NM# ||'%'\n </isNotEmpty>\n \n <isNotEmpty property=\"dsCond.REG_USER_NM\">\n AND E.REG_USER_NM LIKE '%'|| #dsCond.REG_USER_NM# ||'%'\n </isNotEmpty>\n ");
		info.setResultNameSet(resultNameSet);
		info.setParameterSet(tmpParameterSet);
		EventHandler eventHandler = new EventHandler();
		invoke(info, eventHandler, globalParameterSet);

	}
	public void useronAfterExecute(ParameterSet globalParameterSet, InvokingInfo invokingInfo, DataSource dataSource) throws AutomationFailException {
		
	}
	public void useronBeforeExecute(ParameterSet globalParameterSet, InvokingInfo invokingInfo, DataSource dataSource) throws AutomationFailException {
		
	}
	public void useronExceptionOccured(ParameterSet globalParameterSet, InvokingInfo invokingInfo, Throwable e, InvokingErrorInfo errorInfo) throws AutomationFailException {
		throw new AutomationFailException(e.getMessage(), e);
	}


}