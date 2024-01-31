package or.sabang.sys.spt.sts.service;

import java.util.List;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.sys.fck.apr.service.FckAprComptVO;
import or.sabang.sys.fck.mse.service.FckMseComptVO;
import or.sabang.sys.fck.pcs.service.FckPcsComptVO;
import or.sabang.sys.lss.bsc.service.LssBscSvyComptVO;
import or.sabang.sys.lss.cnl.service.LssCnlSvyComptVO;
import or.sabang.sys.lss.lcp.service.LssLcpSvyComptVO;
import or.sabang.sys.lss.wka.service.LssWkaSvyComptVO;
import or.sabang.sys.vyt.frd.service.VytFrdSvyComptVO;

public interface SptSttusManageService {
	
	/**
	 * @param searchVO
	 * @return
	 * @description 기초조사 연도별 건수 조회
	 */
	public List<EgovMap> selectBscYearSttus(LssBscSvyComptVO searchVO) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @description 기초조사 실태조사 필요성 조회
	 */
	public List<EgovMap> selectBscNcsstySttus(LssBscSvyComptVO searchVO) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @description 기초조사 관할별 건수조회
	 */
	public List<EgovMap> selectBscRegionSttus(LssBscSvyComptVO searchVO) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @description 기초조사 행정구역별 건수조회
	 */
	public List<EgovMap> selectBscAdministSttus(LssBscSvyComptVO searchVO) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @description 기초조사 조사인자 조회
	 */
	public List<EgovMap> selectBscFactorSttus(LssBscSvyComptVO searchVO) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @description 외관점검 연도별 건수 조회
	 */
	public List<EgovMap> selectAprYearSttus(FckAprComptVO searchVO) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @description 외관점검 조사결과 조회
	 */
	public List<EgovMap> selectAprRsltSttus(FckAprComptVO searchVO) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @description 외관점검 조사결과 행정구역별 조회
	 */
	public List<EgovMap> selectAprAdministSttus(FckAprComptVO searchVO) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @description 외관점검 공통조사인자 조회
	 */
	public List<EgovMap> selectAprFactorSttus(FckAprComptVO searchVO) throws Exception;
	
	
	/**
	 * @param searchVO
	 * @return
	 * @description 땅밀림 실태조사 조사결과 행정구역별 조회
	 */
	public List<EgovMap> selectLcpAdministSttus(LssLcpSvyComptVO searchVO) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @description 취약지역 실태조사 조사결과 행정구역별 조회
	 */
	public List<EgovMap> selectWkaAdministSttus(LssWkaSvyComptVO searchVO) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @description 취약지역 실태조사 공통조사인자 조회
	 */
	public List<EgovMap> selectWkaFactorSttus(LssWkaSvyComptVO searchVO) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @description 취약지역 실태조사 조사결과 조회
	 */
	public List<EgovMap> selectWkaRsltSttus(LssWkaSvyComptVO searchVO) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @description 취약지역 해제조사 조사결과 행정구역별 조회
	 */
	public List<EgovMap> selectCnlAdministSttus(LssCnlSvyComptVO searchVO) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @description 취약지역 해제조사 공통조사인자 조회
	 */
	public List<EgovMap> selectCnlFactorSttus(LssCnlSvyComptVO searchVO) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @description 취약지역 해제조사 조사결과 조회
	 */
	public List<EgovMap> selectCnlRsltSttus(LssCnlSvyComptVO searchVO) throws Exception;

	/**
	 * 땅밀림 실태조사 판정등급별 
	 * @param searchVO
	 * @return
	 * @description 땅밀림 실태조사 판정등급별 
	 * @throws Exception
	 */
	public List<EgovMap> selectLcpLastgrdSttus(LssLcpSvyComptVO searchVO) throws Exception;

	/**
	 * 땅밀림 실태조사 주 구성암석별 
	 * @param searchVO
	 * @return
	 * @description 땅밀림 실태조사 주 구성암석별 
	 * @throws Exception
	 */
	public List<EgovMap> selectLcpCmprokvalSttus(LssLcpSvyComptVO searchVO) throws Exception;

	/**
	 * 땅밀림 실태조사 타 지층 및 관입암 
	 * @param searchVO
	 * @return
	 * @description 땅밀림 실태조사 타 지층 및 관입암 
	 * @throws Exception
	 */
	public List<EgovMap> selectLcpInstrokatSttus(LssLcpSvyComptVO searchVO) throws Exception;

	/**
	 * 땅밀림 실태조사 암석 풍화 
	 * @param searchVO
	 * @return
	 * @description 땅밀림 실태조사 암석 풍화 
	 * @throws Exception
	 */
	public List<EgovMap> selectLcpRokwthrvalSttus(LssLcpSvyComptVO searchVO) throws Exception;

	/**
	 * 땅밀림 실태조사 지질구조 
	 * @param searchVO
	 * @return
	 * @description 땅밀림 실태조사 지질구조 
	 * @throws Exception
	 */
	public List<EgovMap> selectLcpGeologySttus(LssLcpSvyComptVO searchVO) throws Exception;

	/**
	 * 땅밀림 실태조사 토양형 
	 * @param searchVO
	 * @return
	 * @description 땅밀림 실태조사 토양형 
	 * @throws Exception
	 */
	public List<EgovMap> selectLcpSoiltySttus(LssLcpSvyComptVO searchVO) throws Exception;

	/**
	 * 땅밀림 실태조사 토심(cm) B층 까지 깊이 
	 * @param searchVO
	 * @return
	 * @description 땅밀림 실태조사 토심(cm) B층 까지 깊이 
	 * @throws Exception
	 */
	public List<EgovMap> selectLcpSoildeptbvalSttus(LssLcpSvyComptVO searchVO) throws Exception;

	/**
	 * 땅밀림 실태조사 토성 B층 기준 (약 30cm 부근) 
	 * @param searchVO
	 * @return
	 * @description 땅밀림 실태조사 토성 B층 기준 (약 30cm 부근) 
	 * @throws Exception
	 */
	public List<EgovMap> selectLcpSoilclassbvalSttus(LssLcpSvyComptVO searchVO) throws Exception;

	/**
	 * 땅밀림 실태조사 토양 구조 B층 기준 
	 * @param searchVO
	 * @return
	 * @description 땅밀림 실태조사 토양 구조 B층 기준 
	 * @throws Exception
	 */
	public List<EgovMap> selectLcpSoilstrctSttus(LssLcpSvyComptVO searchVO) throws Exception;

	/**
	 * 땅밀림 실태조사 토양 수분 상태 
	 * @param searchVO
	 * @return
	 * @description 땅밀림 실태조사 토양 수분 상태 
	 * @throws Exception
	 */
	public List<EgovMap> selectLcpSoilwtrvalSttus(LssLcpSvyComptVO searchVO) throws Exception;

	/**
	 * 땅밀림 실태조사 암석 노출도 
	 * @param searchVO
	 * @return
	 * @description 땅밀림 실태조사 암석 노출도 
	 * @throws Exception
	 */
	public List<EgovMap> selectLcpRokexpsrSttus(LssLcpSvyComptVO searchVO) throws Exception;

	/**
	 * 땅밀림 실태조사 너덜유무 
	 * @param searchVO
	 * @return
	 * @description 땅밀림 실태조사 너덜유무 
	 * @throws Exception
	 */
	public List<EgovMap> selectLcpTalusatSttus(LssLcpSvyComptVO searchVO) throws Exception;

	/**
	 * 땅밀림 실태조사 지형구분 
	 * @param searchVO
	 * @return
	 * @description 땅밀림 실태조사 지형구분 
	 * @throws Exception
	 */
	public List<EgovMap> selectLcpTpgrphvalSttus(LssLcpSvyComptVO searchVO) throws Exception;

	/**
	 * 땅밀림 실태조사 지형특성 
	 * @param searchVO
	 * @return
	 * @description 땅밀림 실태조사 지형특성 
	 * @throws Exception
	 */
	public List<EgovMap> selectLcpLnformSttus(LssLcpSvyComptVO searchVO) throws Exception;

	/**
	 * 땅밀림 실태조사 수리특성 
	 * @param searchVO
	 * @return
	 * @description 땅밀림 실태조사 수리특성 
	 * @throws Exception
	 */
	public List<EgovMap> selectLcpWtrSttus(LssLcpSvyComptVO searchVO) throws Exception;

	/**
	 * 땅밀림 실태조사 임상 
	 * @param searchVO
	 * @return
	 * @description 땅밀림 실태조사 임상 
	 * @throws Exception
	 */
	public List<EgovMap> selectLcpFrstfrvalSttus(LssLcpSvyComptVO searchVO) throws Exception;

	/**
	 * 땅밀림 실태조사 징후여부 
	 * @param searchVO
	 * @return
	 * @description 땅밀림 실태조사 징후여부 
	 * @throws Exception
	 */
	public List<EgovMap> selectLcpDirsymptmSttus(LssLcpSvyComptVO searchVO) throws Exception;

	/**
	 * @param searchVO
	 * @return
	 * @description 취약지역 실태조사 판정등급 현황
	 * @throws Exception
	 */
	public List<EgovMap> selectWkaJdgmntgradSttus(LssWkaSvyComptVO searchVO) throws Exception;

	/**
	 * @param searchVO
	 * @return
	 * @description 취약지역 실태조사 보호시설 현황
	 * @throws Exception
	 */
	public List<EgovMap> selectWkaSafefctSttus(LssWkaSvyComptVO searchVO) throws Exception;

	/**
	 * @param searchVO
	 * @return
	 * @description 취약지역 실태조사 인가/호수 현황
	 * @throws Exception
	 */
	public List<EgovMap> selectWkaHouseLakeSttus(LssWkaSvyComptVO searchVO) throws Exception;

	/**
	 * @param searchVO
	 * @return
	 * @description 취약지역 실태조사 상부주요시설
	 * @throws Exception
	 */
	public List<EgovMap> selectWkaHighmainfctSttus(LssWkaSvyComptVO searchVO) throws Exception;

	/**
	 * @param searchVO
	 * @return
	 * @description 취약지역 실태조사 하부주요시설
	 * @throws Exception
	 */
	public List<EgovMap> selectWkaLowmainfctSttus(LssWkaSvyComptVO searchVO) throws Exception;

	/**
	 * @param searchVO
	 * @return
	 * @description 취약지역 실태조사 임상
	 * @throws Exception
	 */
	public List<EgovMap> selectWkaFrtpSttus(LssWkaSvyComptVO searchVO) throws Exception;

	/**
	 * @param searchVO
	 * @return
	 * @description 취약지역 실태조사 임분밀도
	 * @throws Exception
	 */
	public List<EgovMap> selectWkaStddnstSttus(LssWkaSvyComptVO searchVO) throws Exception;

	/**
	 * @param searchVO
	 * @return
	 * @description 취약지역 실태조사 임분경급
	 * @throws Exception
	 */
	public List<EgovMap> selectWkaStddmclsSttus(LssWkaSvyComptVO searchVO) throws Exception;

	/**
	 * @param searchVO
	 * @return
	 * @description 취약지역 실태조사 사업 가능여부
	 * @throws Exception
	 */
	public List<EgovMap> selectWkaBsposblatSttus(LssWkaSvyComptVO searchVO) throws Exception;

	/**
	 * @param searchVO
	 * @return
	 * @description 취약지역 실태조사 대책방안
	 * @throws Exception
	 */
	public List<EgovMap> selectWkaCntrplnmethodSttus(LssWkaSvyComptVO searchVO) throws Exception;

	/**
	 * @param searchVO
	 * @return
	 * @description 취약지역 해제조사 유역현황
	 * @throws Exception
	 */
	public List<EgovMap> selectCnlDgrsttusSttus(LssCnlSvyComptVO searchVO) throws Exception;

	/**
	 * @param searchVO
	 * @return
	 * @description 취약지역 해제조사 적용공법
	 * @throws Exception
	 */	
	public List<EgovMap> selectCnlApplcegnermhdSttus(LssCnlSvyComptVO searchVO) throws Exception;

	/**
	 * @param searchVO
	 * @return
	 * @description 취약지역 해제조사 산사태등급
	 * @throws Exception
	 */	
	public List<EgovMap> selectCnlLndsldgrdeSttus(LssCnlSvyComptVO searchVO) throws Exception;

	/**
	 * @param searchVO
	 * @return
	 * @description 취약지역 해제조사 평균경사
	 * @throws Exception
	 */	
	public List<EgovMap> selectCnlSlopeavgSttus(LssCnlSvyComptVO searchVO) throws Exception;

	/**
	 * @param searchVO
	 * @return
	 * @description 취약지역 해제조사 임상도
	 * @throws Exception
	 */	
	public List<EgovMap> selectCnlFrtptypeSttus(LssCnlSvyComptVO searchVO) throws Exception;

	/**
	 * @param searchVO
	 * @return
	 * @description 취약지역 해제조사 경급도
	 * @throws Exception
	 */	
	public List<EgovMap> selectCnlDmclstypeSttus(LssCnlSvyComptVO searchVO) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @description 임도 타당성평가 유형별 현황
	 * @throws Exception
	 */	
	public List<EgovMap> selectFrdTypeSttus(VytFrdSvyComptVO searchVO) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @description 임도 타당성평가 조사 목록 조회
	 * @throws Exception
	 */	
	public List<EgovMap> selectFrdSvyItems(VytFrdSvyComptVO searchVO) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @description 임도 타당성평가 관할별 건수조회
	 */
	public List<EgovMap> selectFrdRegionSttus(VytFrdSvyComptVO searchVO) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @description 임도 타당성평가 행정구역별 건수조회
	 */
	public List<EgovMap> selectFrdAdministSttus(VytFrdSvyComptVO searchVO) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @description 계측장비 연도별 건수 조회
	 */
	public List<EgovMap> selectMseYearSttus(FckMseComptVO searchVO) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @description 계측장비 시도별 와이어신축계 장비수 조회
	 */
	public List<EgovMap> selectMseWireAdministSttus(FckMseComptVO searchVO) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @description 계측장비 시도별 지중경사계 장비수 조회
	 */
	public List<EgovMap> selectMseLicmeterAdministSttus(FckMseComptVO searchVO) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @description 계측장비 시도별 지하수위계 장비수 조회
	 */
	public List<EgovMap> selectMseGwGaugeAdministSttus(FckMseComptVO searchVO) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @description 계측장비 시도별 강우계 장비수 조회
	 */
	public List<EgovMap> selectMseRainGaugeAdministSttus(FckMseComptVO searchVO) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @description 계측장비 시도별 지표변위계 장비수 조회
	 */
	public List<EgovMap> selectMseSurDpmAdministSttus(FckMseComptVO searchVO) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @description 계측장비 시도별 GPS변위계 장비수 조회
	 */
	public List<EgovMap> selectMseGpsGaugeAdministSttus(FckMseComptVO searchVO) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @description 계측장비 시도별 게이트웨이 장비수 조회
	 */
	public List<EgovMap> selectMseGatewayAdministSttus(FckMseComptVO searchVO) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @description 계측장비 시도별 노드 장비수 조회
	 */
	public List<EgovMap> selectMseNodeAdministSttus(FckMseComptVO searchVO) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @description 정밀점검 조사 목록 조회
	 * @throws Exception
	 */	
	public List<EgovMap> selectPcsSvyItems(FckPcsComptVO searchVO) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @description 정밀점검 행정구역별 건수조회
	 */
	public List<EgovMap> selectPcsAdministSttus(FckPcsComptVO searchVO) throws Exception;
}
