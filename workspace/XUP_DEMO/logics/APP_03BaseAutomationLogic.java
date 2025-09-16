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

public class APP_03BaseAutomationLogic extends BaseAutomationLogic {

	public ParameterSet execute(ParameterSet inputParameterSet) throws AutomationFailException{
		ParameterSet outputParameterSet = new ParameterSet();
		ParameterSet globalParameterSet = new ParameterSet();
		globalParameterSet.add(inputParameterSet);
		initParameterSet(globalParameterSet);
		start(globalParameterSet);
		
		user(globalParameterSet);
		select(globalParameterSet);

		if(globalParameterSet.getParameter("dsSite") != null){
			outputParameterSet.add(globalParameterSet.getParameter("dsSite"));
		}
		if(globalParameterSet.getParameter("dsDept") != null){
			outputParameterSet.add(globalParameterSet.getParameter("dsDept"));
		}
		
		end(globalParameterSet, outputParameterSet);
		return outputParameterSet;
	}
	private void initParameterSet(ParameterSet globalParameterSet) {
		DataSet dsSite = new DataSet("dsSite");

		dsSite.addColumn("SITE_ID", PlatformDataType.STRING, 50);
		dsSite.addColumn("SITE_NM", PlatformDataType.STRING, 100);

		globalParameterSet.add(dsSite);
		DataSet dsDept = new DataSet("dsDept");

		dsDept.addColumn("UPPO_SITE_ID", PlatformDataType.STRING, 50);
		dsDept.addColumn("DEPT_ID", PlatformDataType.STRING, 100);
		dsDept.addColumn("DEPT_NM", PlatformDataType.STRING, 255);
		dsDept.addColumn("EMP_ID", PlatformDataType.STRING, 255);
		dsDept.addColumn("EMP_NM", PlatformDataType.STRING, 255);
		dsDept.addColumn("POSITION_CD", PlatformDataType.STRING, 255);
		dsDept.addColumn("POSITION_NM", PlatformDataType.STRING, 255);

		globalParameterSet.add(dsDept);
	}
	public void start(ParameterSet globalParameterSet)throws AutomationFailException {//!findOffset_start!
	}
	public void end(ParameterSet globalParameterSet, ParameterSet outputParameterSet)throws AutomationFailException {//!findOffset_end!
	}
	public void user(ParameterSet globalParameterSet) throws AutomationFailException {//!findOffset_user!
		ParameterSet tmpParameterSet = new ParameterSet();

		ResultNameSet resultNameSet = new ResultNameSet();
		resultNameSet.add("RESULT0", "dsSite", null);

		DbSelectInvokingInfo info = new DbSelectInvokingInfo();
		info.setDomainName("XUP_DEMO");
		info.setDataSourceName("NEXACRO_DEMO");
		info.setSqlSelect("SELECT\n DISTINCT\n SITE_ID, 				/*사업소 ID*/\n SITE_NM 				/*사업소 명*/\n FROM EMPLOYEE\n ORDER BY SITE_ID\n ");
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
	public void select(ParameterSet globalParameterSet) throws AutomationFailException {//!findOffset_select!
		ParameterSet tmpParameterSet = new ParameterSet();

		ResultNameSet resultNameSet = new ResultNameSet();
		resultNameSet.add("RESULT0", "dsDept", null);

		DbSelectInvokingInfo info = new DbSelectInvokingInfo();
		info.setDomainName("XUP_DEMO");
		info.setDataSourceName("NEXACRO_DEMO");
		info.setSqlSelect("SELECT\n DISTINCT\n SITE_ID AS UPPO_SITE_ID, 	/*사업소 ID*/\n DEPT_ID,					/*부서 ID*/\n DEPT_NM, 					/*부서 명*/\n EMP_ID,						/*사번*\n NAME AS EMP_NM,				/*이름*/\n POSITION_CD,				/*직급 코드*/\n POSITION_NM					/*직급 명*/\n FROM EMPLOYEE\n ORDER BY SITE_ID, DEPT_ID ASC\n ");
		info.setResultNameSet(resultNameSet);
		info.setParameterSet(tmpParameterSet);
		EventHandler eventHandler = new EventHandler();
		invoke(info, eventHandler, globalParameterSet);

	}
	public void selectonAfterExecute(ParameterSet globalParameterSet, InvokingInfo invokingInfo, DataSource dataSource) throws AutomationFailException {
		
	}
	public void selectonBeforeExecute(ParameterSet globalParameterSet, InvokingInfo invokingInfo, DataSource dataSource) throws AutomationFailException {
		
	}
	public void selectonExceptionOccured(ParameterSet globalParameterSet, InvokingInfo invokingInfo, Throwable e, InvokingErrorInfo errorInfo) throws AutomationFailException {
		throw new AutomationFailException(e.getMessage(), e);
	}


}