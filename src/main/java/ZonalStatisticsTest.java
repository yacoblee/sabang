import com.supermap.analyst.spatialanalyst.GridStatisticsMode;
import com.supermap.analyst.spatialanalyst.StatisticsAnalyst;
import com.supermap.analyst.spatialanalyst.ZonalStatisticsAnalystParameter;
import com.supermap.analyst.spatialanalyst.ZonalStatisticsAnalystResult;
import com.supermap.data.Dataset;
import com.supermap.data.DatasetGrid;
import com.supermap.data.Datasets;
import com.supermap.data.Datasource;
import com.supermap.data.DatasourceConnectionInfo;
import com.supermap.data.Datasources;
import com.supermap.data.EngineType;
import com.supermap.data.PixelFormat;
import com.supermap.data.Workspace;

public class ZonalStatisticsTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//작업공간을 열고 데이터소스 가져오기
		Workspace workspace = new Workspace();
		Datasources datasources = workspace.getDatasources();
//		DatasourceConnectionInfo connectionInfo = new DatasourceConnectionInfo();
//
//		String server = "125.132.237.63:20223";
//		String database = "sabang";
//		String alias = "PostgreSQL";
//
//		//연결정보 입력하기
//		connectionInfo.setEngineType(EngineType.PGGIS);
//		connectionInfo.setServer(server);
//		connectionInfo.setDatabase(database);
//		connectionInfo.setUser("sabangdb");
//		connectionInfo.setPassword("podo1234!");
//		connectionInfo.setAlias(alias);

		DatasourceConnectionInfo connectionInfo = new DatasourceConnectionInfo();
		connectionInfo.setServer("E:/문서/사방협회/DATA/00. DataSource/sabangDataTest.udbx");
		connectionInfo.setReadOnly(false);
		connectionInfo.setAlias("sample");
		connectionInfo.setEngineType(EngineType.UDBX);
		
		Datasource datasource = datasources.open(connectionInfo);
		Datasets datasets = datasource.getDatasets();
		
//		String zonalName = "tf_feis_lcp_rank";
//		String valueName = "tf_feis_dem";
//		String outputDatasetName = "ta_feis_lcp_rank_result";
//		String outputTableName = "ta_feis_lcp_rank_result_tb";
//		String zonalFieldName = "SmID";

		String zonalName = "tf_feis_lcp_rank";
		String valueName = "tf_feis_dem_raster_10";
		String outputDatasetName = "ta_feis_lcp_rank_result";
		String outputTableName = "ta_feis_lcp_rank_result_tb";
		String zonalFieldName = "SmID";
		
		Dataset zonalDataset = (Dataset) datasets.get(zonalName);
		DatasetGrid valueDataset = (DatasetGrid) datasets.get(valueName);

		if (datasets.contains(outputDatasetName))
		{
			datasets.delete(outputDatasetName);
		}

		if (datasets.contains(outputTableName))
		{
			datasets.delete(outputTableName);
		}

		ZonalStatisticsAnalystParameter parameter = new ZonalStatisticsAnalystParameter();
		parameter.setTargetDatasource(datasource);
		parameter.setTargetDatasetName(outputDatasetName);
		parameter.setTargetTableName(outputTableName);
		parameter.setZonalFieldName(zonalFieldName);
		parameter.setIgnoreNoValue(true);
		parameter.setStatisticsMode(GridStatisticsMode.SUM);
		//parameter.setPixelFormat(PixelFormat.DOUBLE);
		parameter.setValueDataset(valueDataset);
		parameter.setZonalDataset(zonalDataset);

		ZonalStatisticsAnalystResult zonalStatisticResult = StatisticsAnalyst.zonalStatisticsOnRasterValue(parameter);
		DatasetGrid resultDatasetGrid = zonalStatisticResult.getResultDatasetGrid();
		
		System.out.println("finished...");
	}

}
