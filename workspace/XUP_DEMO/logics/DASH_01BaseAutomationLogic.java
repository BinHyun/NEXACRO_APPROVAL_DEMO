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

public class DASH_01BaseAutomationLogic extends BaseAutomationLogic {

	public ParameterSet execute(ParameterSet inputParameterSet) throws AutomationFailException{
		ParameterSet outputParameterSet = new ParameterSet();
		ParameterSet globalParameterSet = new ParameterSet();
		globalParameterSet.add(inputParameterSet);
		initParameterSet(globalParameterSet);
		start(globalParameterSet);
		
		user(globalParameterSet);

		if(globalParameterSet.getParameter("dsList") != null){
			outputParameterSet.add(globalParameterSet.getParameter("dsList"));
		}
		
		end(globalParameterSet, outputParameterSet);
		return outputParameterSet;
	}
	private void initParameterSet(ParameterSet globalParameterSet) {
		DataSet dsList = new DataSet("dsList");

		dsList.addColumn("YEAR", PlatformDataType.STRING, 255);
		dsList.addColumn("MONTH", PlatformDataType.STRING, 255);
		dsList.addColumn("TITLE", PlatformDataType.STRING, 255);
		dsList.addColumn("FILE_NM", PlatformDataType.STRING, 255);
		dsList.addColumn("ATT_TYPE_NM", PlatformDataType.STRING, 255);
		dsList.addColumn("SITE_NM", PlatformDataType.STRING, 255);
		dsList.addColumn("DEPT_NM", PlatformDataType.STRING, 255);
		dsList.addColumn("USER_NM", PlatformDataType.STRING, 255);

		globalParameterSet.add(dsList);
	}
	public void start(ParameterSet globalParameterSet)throws AutomationFailException {//!findOffset_start!
	}
	public void end(ParameterSet globalParameterSet, ParameterSet outputParameterSet)throws AutomationFailException {//!findOffset_end!
	}
	public void user(ParameterSet globalParameterSet) throws AutomationFailException {//!findOffset_user!
		ParameterSet tmpParameterSet = new ParameterSet();
		tmpParameterSet.add(globalParameterSet.getParameter("dsCond"));

		ResultNameSet resultNameSet = new ResultNameSet();
		resultNameSet.add("RESULT0", "dsList", null);

		DbSelectInvokingInfo info = new DbSelectInvokingInfo();
		info.setDomainName("XUP_DEMO");
		info.setDataSourceName("NEXACRO_DEMO");
		info.setSqlSelect("SELECT\n EXTRACT(YEAR FROM D.REG_DT) AS YEAR, 			/*등록연도*/\n EXTRACT(MONTH FROM D.REG_DT) AS MONTH,			/*등록월*/\n D.EV_TITLE AS TITLE,							/*등록명*/\n F.FILE_NM AS FILE_NM,							/*첨부 파일명*/\n F.ATT_TYPE_NM AS ATT_TYPE_NM,					/*첨부 파일 종류*/\n D.SITE_NM AS SITE_NM,							/*사업장 명*/\n D.DEPT_NM AS DEPT_NM,							/*부서 명*/\n D.REG_USER_NM AS USER_NM						/*등록자 명*/\n FROM EVD_DOC D, EVD_FILE F\n WHERE D.EV_ID = F.EV_ID\n AND EXTRACT(YEAR FROM D.REG_DT) = #dsCond.YEAR#\n AND EXTRACT(MONTH FROM D.REG_DT) = #dsCond.MONTH#\n \n <isNotEmpty property=\"dsCond.TITLE\">\n AND D.EV_TITLE LIKE '%'||#dsCond.TITLE# ||'%'\n </isNotEmpty>\n \n <isNotEmpty property=\"dsCond.FILE_EXT\">\n AND F.ATT_TYPE_NM = #dsCond.FILE_EXT#\n </isNotEmpty>\n \n <isNotEmpty property=\"dsCond.SITE_NM\">\n AND D.SITE_NM LIKE '%'|| #dsCond.SITE_NM# ||'%'\n </isNotEmpty>\n \n <isNotEmpty property=\"dsCond.DEPT_NM\">\n AND D.DEPT_NM LIKE '%'|| #dsCond.DEPT_NM# ||'%'\n </isNotEmpty>\n \n <isNotEmpty property=\"dsCond.REG_USER_NM\">\n AND D.REG_USER_NM LIKE '%'|| #dsCond.REG_USER_NM# ||'%'\n </isNotEmpty>\n \n ");
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