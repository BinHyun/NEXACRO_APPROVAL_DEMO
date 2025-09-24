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

public class EVD_04BaseAutomationLogic extends BaseAutomationLogic {

	public ParameterSet execute(ParameterSet inputParameterSet) throws AutomationFailException{
		ParameterSet outputParameterSet = new ParameterSet();
		ParameterSet globalParameterSet = new ParameterSet();
		globalParameterSet.add(inputParameterSet);
		initParameterSet(globalParameterSet);
		start(globalParameterSet);
		
		modify(globalParameterSet);

		if(globalParameterSet.getParameter("RESULT0") != null){
			outputParameterSet.add(globalParameterSet.getParameter("RESULT0"));
		}
		
		end(globalParameterSet, outputParameterSet);
		return outputParameterSet;
	}
	private void initParameterSet(ParameterSet globalParameterSet) {
		DataSet RESULT0 = new DataSet("RESULT0");

		RESULT0.addColumn("ROWINDEX", PlatformDataType.INT, 255);
		RESULT0.addColumn("STATUS", PlatformDataType.STRING, 255);
		RESULT0.addColumn("RESULT", PlatformDataType.INT, 255);
		RESULT0.addColumn("ERROR", PlatformDataType.STRING, 255);

		globalParameterSet.add(RESULT0);
	}
	public void start(ParameterSet globalParameterSet)throws AutomationFailException {//!findOffset_start!
	}
	public void end(ParameterSet globalParameterSet, ParameterSet outputParameterSet)throws AutomationFailException {//!findOffset_end!
	}
	public void modify(ParameterSet globalParameterSet) throws AutomationFailException {//!findOffset_modify!
		ParameterSet tmpParameterSet = new ParameterSet();
		tmpParameterSet.add(globalParameterSet.getParameter("dsSendApprove"));

		ResultNameSet resultNameSet = new ResultNameSet();
		resultNameSet.add("RESULT0", "RESULT0", null);

		// defaultBindingValueMap
		Map defaultBindingValueMap = new HashMap();
		// SqlTypeBindingInfo
		SqlTypeBindingInfo sqlTypeBindingInfo = new SqlTypeBindingInfo();
		sqlTypeBindingInfo.setUseDefaultBinding(true);
		// DataSetRowActionFilter
		DataSetRowProcessConditionInfoSet dataSetRowActionFilter = new DataSetRowProcessConditionInfoSet();
		DbModifyInvokingInfo info = new DbModifyInvokingInfo();
		info.setDomainName("XUP_DEMO");
		info.setDataSourceName("NEXACRO_DEMO");
		info.setSqlInsert("UPDATE 	APV_DOC\n SET APV_STATUS_CD = #dsSendApprove.APV_STATUS_CD#\n , SUBMITTED_AT = TO_DATE(SYSDATE, 'YY/MM/DD')\n , L1_EMP_NO = #dsSendApprove.L1_EMP_NO#\n , L1_USER_NM = #dsSendApprove.L1_USER_NM#\n , L1_POSITION_NM = #dsSendApprove.L1_POSITION_NM#\n , L1_STATUS_CD = #dsSendApprove.L1_ACTED_AT#\n , L1_ACTED_AT = TO_DATE(SYSDATE, 'YY/MM/DD')\n , L1_COMMENT = #dsSendApprove.L1_COMMENT#\n , L2_EMP_NO = #dsSendApprove.L2_EMP_NO#\n , L2_USER_NM = #dsSendApprove.L2_USER_NM#\n , L2_POSITION_NM = #dsSendApprove.L2_POSITION_NM#\n , L2_STATUS_CD = #dsSendApprove.L2_STATUS_CD#\n , L2_ACTED_AT = #dsSendApprove.L2_ACTED_AT#\n , L2_COMMENT = #dsSendApprove.L2_COMMENT#\n , L3_EMP_NO = #dsSendApprove.L3_EMP_NO#\n , L3_USER_NM = #dsSendApprove.L3_USER_NM#\n , L3_POSITION_NM = #dsSendApprove.L3_POSITION_NM#\n , L3_STATUS_CD = #dsSendApprove.L3_STATUS_CD#\n , L3_ACTED_AT = #dsSendApprove.L3_ACTED_AT#\n , L3_COMMENT = #dsSendApprove.L3_COMMENT#\n , L4_EMP_NO = #dsSendApprove.L4_EMP_NO#\n , L4_USER_NM = #dsSendApprove.L4_USER_NM#\n , L4_POSITION_NM = #dsSendApprove.L4_POSITION_NM#\n , L4_STATUS_CD = #dsSendApprove.L4_STATUS_CD#\n , L4_ACTED_AT = #dsSendApprove.L4_ACTED_AT#\n , L4_COMMENT = #dsSendApprove.L4_COMMENT#\n , L5_EMP_NO = #dsSendApprove.L5_EMP_NO#\n , L5_USER_NM = #dsSendApprove.L5_USER_NM#\n , L5_POSITION_NM = #dsSendApprove.L5_POSITION_NM#\n , L5_STATUS_CD = #dsSendApprove.L5_STATUS_CD#\n , L5_ACTED_AT = #dsSendApprove.L5_ACTED_AT#\n , L5_COMMENT = #dsSendApprove.L5_COMMENT#\n , UPDATED_AT = SYSDATE\n WHERE APV_ID = #dsSendApprove.APV_ID#\n AND EV_ID = #dsSendApprove.EV_ID#\n ");
		info.setSqlUpdate("UPDATE 	APV_DOC\n SET APV_STATUS_CD = #dsSendApprove.APV_STATUS_CD#\n , SUBMITTED_AT = TO_DATE(SYSDATE, 'YY/MM/DD')\n , L1_EMP_NO = #dsSendApprove.L1_EMP_NO#\n , L1_USER_NM = #dsSendApprove.L1_USER_NM#\n , L1_POSITION_NM = #dsSendApprove.L1_POSITION_NM#\n , L1_STATUS_CD = #dsSendApprove.L1_ACTED_AT#\n , L1_ACTED_AT = TO_DATE(SYSDATE, 'YY/MM/DD')\n , L1_COMMENT = #dsSendApprove.L1_COMMENT#\n , L2_EMP_NO = #dsSendApprove.L2_EMP_NO#\n , L2_USER_NM = #dsSendApprove.L2_USER_NM#\n , L2_POSITION_NM = #dsSendApprove.L2_POSITION_NM#\n , L2_STATUS_CD = #dsSendApprove.L2_STATUS_CD#\n , L2_ACTED_AT = #dsSendApprove.L2_ACTED_AT#\n , L2_COMMENT = #dsSendApprove.L2_COMMENT#\n , L3_EMP_NO = #dsSendApprove.L3_EMP_NO#\n , L3_USER_NM = #dsSendApprove.L3_USER_NM#\n , L3_POSITION_NM = #dsSendApprove.L3_POSITION_NM#\n , L3_STATUS_CD = #dsSendApprove.L3_STATUS_CD#\n , L3_ACTED_AT = #dsSendApprove.L3_ACTED_AT#\n , L3_COMMENT = #dsSendApprove.L3_COMMENT#\n , L4_EMP_NO = #dsSendApprove.L4_EMP_NO#\n , L4_USER_NM = #dsSendApprove.L4_USER_NM#\n , L4_POSITION_NM = #dsSendApprove.L4_POSITION_NM#\n , L4_STATUS_CD = #dsSendApprove.L4_STATUS_CD#\n , L4_ACTED_AT = #dsSendApprove.L4_ACTED_AT#\n , L4_COMMENT = #dsSendApprove.L4_COMMENT#\n , L5_EMP_NO = #dsSendApprove.L5_EMP_NO#\n , L5_USER_NM = #dsSendApprove.L5_USER_NM#\n , L5_POSITION_NM = #dsSendApprove.L5_POSITION_NM#\n , L5_STATUS_CD = #dsSendApprove.L5_STATUS_CD#\n , L5_ACTED_AT = #dsSendApprove.L5_ACTED_AT#\n , L5_COMMENT = #dsSendApprove.L5_COMMENT#\n , UPDATED_AT = SYSDATE\n WHERE APV_ID = #dsSendApprove.APV_ID#\n AND EV_ID = #dsSendApprove.EV_ID#\n ");
		info.setSqlDelete("");
		info.setResultNameSet(resultNameSet);
		info.setParameterSet(tmpParameterSet);
		info.setLoopTargetDataSetName("dsSendApprove");
		info.setSqlTypeBindingInfo(sqlTypeBindingInfo);
		info.setDataSetRowActionFilter(dataSetRowActionFilter);
		info.setDefaultBindingValueMap(defaultBindingValueMap);
		EventHandler eventHandler = new EventHandler();
		invoke(info, eventHandler, globalParameterSet);

	}
	public void modifyonAfterExecute(ParameterSet globalParameterSet, InvokingInfo invokingInfo, DataSource dataSource) throws AutomationFailException {
		
	}
	public void modifyonBeforeExecute(ParameterSet globalParameterSet, InvokingInfo invokingInfo, DataSource dataSource) throws AutomationFailException {
		
	}
	public void modifyonExceptionOccured(ParameterSet globalParameterSet, InvokingInfo invokingInfo, Throwable e, InvokingErrorInfo errorInfo) throws AutomationFailException {
		throw new AutomationFailException(e.getMessage(), e);
	}


}