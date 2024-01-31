package or.sabang.sys.spt.sts.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.sys.fck.apr.service.FckAprComptVO;
import or.sabang.sys.fck.mse.service.FckMseComptVO;
import or.sabang.sys.fck.pcs.service.FckPcsComptVO;
import or.sabang.sys.lss.bsc.service.LssBscSvyComptVO;
import or.sabang.sys.lss.cnl.service.LssCnlSvyComptVO;
import or.sabang.sys.lss.lcp.service.LssLcpSvyComptVO;
import or.sabang.sys.lss.wka.service.LssWkaSvyComptVO;
import or.sabang.sys.spt.sts.service.SptSttusManageService;
import or.sabang.sys.vyt.frd.service.VytFrdSvyComptVO;

@Service("sptSttusManageService")
public class SptSttusManageServiceImpl  extends EgovAbstractServiceImpl implements  SptSttusManageService  {
	
	@Resource(name="sttusManageDAO")
	private SptSttusManageDAO sttusManageDAO;
	
	/**
	 * @param sttusManageDAO
	 * @return
	 * @description 기초조사 연도별 건수 조회
	 */
	@Override
	public List<EgovMap> selectBscYearSttus(LssBscSvyComptVO searchVO) throws Exception {
		return sttusManageDAO.selectBscYearSttus(searchVO);
	}
	
	/**
	 * @param sttusManageDAO
	 * @return
	 * @description 기초조사 실태조사 필요성 조회
	 */
	@Override
	public List<EgovMap> selectBscNcsstySttus(LssBscSvyComptVO searchVO) throws Exception {
		return sttusManageDAO.selectBscNcsstySttus(searchVO);
	}
	
	/**
	 * @param searchVO
	 * @return
	 * @description 기초조사 관할별 건수조회
	 */
	@Override
	public List<EgovMap> selectBscRegionSttus(LssBscSvyComptVO searchVO) throws Exception {
		return sttusManageDAO.selectBscRegionSttus(searchVO);
	}
	
	/**
	 * @param searchVO
	 * @return
	 * @description 기초조사 행정구역별 건수조회
	 */
	@Override
	public List<EgovMap> selectBscAdministSttus(LssBscSvyComptVO searchVO) throws Exception {
		return sttusManageDAO.selectBscAdministSttus(searchVO);
	}
	
	/**
	 * @param sttusManageDAO
	 * @return
	 * @description 기초조사 조사인자 조회
	 */
	@Override
	public List<EgovMap> selectBscFactorSttus(LssBscSvyComptVO searchVO) throws Exception {
		return sttusManageDAO.selectBscFactorSttus(searchVO);
	}

	/**
	 * @param searchVO
	 * @return
	 * @description 외관점검 연도별 건수 조회
	 */
	@Override
	public List<EgovMap> selectAprYearSttus(FckAprComptVO searchVO) throws Exception {
		return sttusManageDAO.selectAprYearSttus(searchVO);
	}

	/**
	 * @param searchVO
	 * @return
	 * @description 외관점검 조사결과 조회
	 */
	@Override
	public List<EgovMap> selectAprRsltSttus(FckAprComptVO searchVO) throws Exception {
		return sttusManageDAO.selectAprRsltSttus(searchVO);
	}

	/**
	 * @param searchVO
	 * @return
	 * @description 외관점검 조사결과 행정구역별 조회
	 */
	@Override
	public List<EgovMap> selectAprAdministSttus(FckAprComptVO searchVO) throws Exception {
		return sttusManageDAO.selectAprAdministSttus(searchVO);
	}

	/**
	 * @param searchVO
	 * @return
	 * @description 외관점검 조사인자 조회
	 */
	@Override
	public List<EgovMap> selectAprFactorSttus(FckAprComptVO searchVO) throws Exception {
		return sttusManageDAO.selectAprFactorSttus(searchVO);
	}
	
	/**
	 * @param searchVO
	 * @return
	 * @description 땅밀림 실태조사 조사결과 행정구역별 조회
	 */
	@Override
	public List<EgovMap> selectLcpAdministSttus(LssLcpSvyComptVO searchVO) throws Exception {
		return sttusManageDAO.selectLcpAdministSttus(searchVO);
	}

	/**
	 * @param searchVO
	 * @return
	 * @description 취약지역 실태조사 조사결과 행정구역별 조회
	 */
	@Override
	public List<EgovMap> selectWkaAdministSttus(LssWkaSvyComptVO searchVO) throws Exception {
		return sttusManageDAO.selectWkaAdministSttus(searchVO);
	}

	/**
	 * @param searchVO
	 * @return
	 * @description 취약지역 실태조사 조사인자 조회
	 */
	@Override
	public List<EgovMap> selectWkaFactorSttus(LssWkaSvyComptVO searchVO) throws Exception {
		return sttusManageDAO.selectWkaFactorSttus(searchVO);
	}

	/**
	 * @param searchVO
	 * @return
	 * @description 취약지역 실태조사 조사결과 조회
	 */
	@Override
	public List<EgovMap> selectWkaRsltSttus(LssWkaSvyComptVO searchVO) throws Exception {
		return sttusManageDAO.selectWkaRsltSttus(searchVO);
	}
	
	/**
	 * @param searchVO
	 * @return
	 * @description 취약지역 해제조사 조사결과 행정구역별 조회
	 */
	@Override
	public List<EgovMap> selectCnlAdministSttus(LssCnlSvyComptVO searchVO) throws Exception {
		return sttusManageDAO.selectCnlAdministSttus(searchVO);
	}
	
	/**
	 * @param searchVO
	 * @return
	 * @description 취약지역 해제조사 조사인자 조회
	 */
	@Override
	public List<EgovMap> selectCnlFactorSttus(LssCnlSvyComptVO searchVO) throws Exception {
		return sttusManageDAO.selectCnlFactorSttus(searchVO);
	}
	
	/**
	 * @param searchVO
	 * @return
	 * @description 취약지역 해제조사 조사결과 조회
	 */
	@Override
	public List<EgovMap> selectCnlRsltSttus(LssCnlSvyComptVO searchVO) throws Exception {
		return sttusManageDAO.selectCnlRsltSttus(searchVO);
	}

	/**
	 * 땅밀림 실태조사 판정등급별 
	 * @param searchVO
	 * @return
	 * @description 땅밀림 실태조사 판정등급별 
	 * @throws Exception
	 */
	@Override
	public List<EgovMap> selectLcpLastgrdSttus(LssLcpSvyComptVO searchVO) throws Exception {
		return sttusManageDAO.selectLcpLastgrdSttus(searchVO);
	}

	/**
	 * 땅밀림 실태조사 주 구성암석별 
	 * @param searchVO
	 * @return
	 * @description 땅밀림 실태조사 주 구성암석별 
	 * @throws Exception
	 */
	@Override
	public List<EgovMap> selectLcpCmprokvalSttus(LssLcpSvyComptVO searchVO) throws Exception {
		return sttusManageDAO.selectLcpCmprokvalSttus(searchVO);
	}

	/**
	 * 땅밀림 실태조사 타 지층 및 관입암 
	 * @param searchVO
	 * @return
	 * @description 땅밀림 실태조사 타 지층 및 관입암 
	 * @throws Exception
	 */
	@Override
	public List<EgovMap> selectLcpInstrokatSttus(LssLcpSvyComptVO searchVO) throws Exception {
		return sttusManageDAO.selectLcpInstrokatSttus(searchVO);
	}

	/**
	 * 땅밀림 실태조사 암석 풍화 
	 * @param searchVO
	 * @return
	 * @description 땅밀림 실태조사 암석 풍화 
	 * @throws Exception
	 */
	@Override
	public List<EgovMap> selectLcpRokwthrvalSttus(LssLcpSvyComptVO searchVO) throws Exception {
		return sttusManageDAO.selectLcpRokwthrvalSttus(searchVO);
	}

	/**
	 * 땅밀림 실태조사 지질구조 
	 * @param searchVO
	 * @return
	 * @description 땅밀림 실태조사 지질구조 
	 * @throws Exception
	 */
	@Override
	public List<EgovMap> selectLcpGeologySttus(LssLcpSvyComptVO searchVO) throws Exception {
		return sttusManageDAO.selectLcpGeologySttus(searchVO);
	}

	/**
	 * 땅밀림 실태조사 토양형 
	 * @param searchVO
	 * @return
	 * @description 땅밀림 실태조사 토양형 
	 * @throws Exception
	 */
	@Override
	public List<EgovMap> selectLcpSoiltySttus(LssLcpSvyComptVO searchVO) throws Exception {
		return sttusManageDAO.selectLcpSoiltySttus(searchVO);
	}

	/**
	 * 땅밀림 실태조사 토심(cm) B층 까지 깊이 
	 * @param searchVO
	 * @return
	 * @description 땅밀림 실태조사 토심(cm) B층 까지 깊이 
	 * @throws Exception
	 */
	@Override
	public List<EgovMap> selectLcpSoildeptbvalSttus(LssLcpSvyComptVO searchVO) throws Exception {
		return sttusManageDAO.selectLcpSoildeptbvalSttus(searchVO);
	}

	/**
	 * 땅밀림 실태조사 토성 B층 기준 (약 30cm 부근) 
	 * @param searchVO
	 * @return
	 * @description 땅밀림 실태조사 토성 B층 기준 (약 30cm 부근) 
	 * @throws Exception
	 */
	@Override
	public List<EgovMap> selectLcpSoilclassbvalSttus(LssLcpSvyComptVO searchVO) throws Exception {
		return sttusManageDAO.selectLcpSoilclassbvalSttus(searchVO);
	}

	/**
	 * 땅밀림 실태조사 토양 구조 B층 기준 
	 * @param searchVO
	 * @return
	 * @description 땅밀림 실태조사 토양 구조 B층 기준 
	 * @throws Exception
	 */
	@Override
	public List<EgovMap> selectLcpSoilstrctSttus(LssLcpSvyComptVO searchVO) throws Exception {
		return sttusManageDAO.selectLcpSoilstrctSttus(searchVO);
	}

	/**
	 * 땅밀림 실태조사 토양 수분 상태 
	 * @param searchVO
	 * @return
	 * @description 땅밀림 실태조사 토양 수분 상태 
	 * @throws Exception
	 */
	@Override
	public List<EgovMap> selectLcpSoilwtrvalSttus(LssLcpSvyComptVO searchVO) throws Exception {
		return sttusManageDAO.selectLcpSoilwtrvalSttus(searchVO);
	}

	/**
	 * 땅밀림 실태조사 암석 노출도 
	 * @param searchVO
	 * @return
	 * @description 땅밀림 실태조사 암석 노출도 
	 * @throws Exception
	 */
	@Override
	public List<EgovMap> selectLcpRokexpsrSttus(LssLcpSvyComptVO searchVO) throws Exception {
		return sttusManageDAO.selectLcpRokexpsrSttus(searchVO);
	}

	/**
	 * 땅밀림 실태조사 너덜유무 
	 * @param searchVO
	 * @return
	 * @description 땅밀림 실태조사 너덜유무 
	 * @throws Exception
	 */
	@Override
	public List<EgovMap> selectLcpTalusatSttus(LssLcpSvyComptVO searchVO) throws Exception {
		return sttusManageDAO.selectLcpTalusatSttus(searchVO);
	}

	/**
	 * 땅밀림 실태조사 지형구분 
	 * @param searchVO
	 * @return
	 * @description 땅밀림 실태조사 지형구분 
	 * @throws Exception
	 */
	@Override
	public List<EgovMap> selectLcpTpgrphvalSttus(LssLcpSvyComptVO searchVO) throws Exception {
		return sttusManageDAO.selectLcpTpgrphvalSttus(searchVO);
	}

	/**
	 * 땅밀림 실태조사 지형특성 
	 * @param searchVO
	 * @return
	 * @description 땅밀림 실태조사 지형특성 
	 * @throws Exception
	 */
	@Override
	public List<EgovMap> selectLcpLnformSttus(LssLcpSvyComptVO searchVO) throws Exception {
		return sttusManageDAO.selectLcpLnformSttus(searchVO);
	}

	/**
	 * 땅밀림 실태조사 수리특성 
	 * @param searchVO
	 * @return
	 * @description 땅밀림 실태조사 수리특성 
	 * @throws Exception
	 */
	@Override
	public List<EgovMap> selectLcpWtrSttus(LssLcpSvyComptVO searchVO) throws Exception {
		return sttusManageDAO.selectLcpWtrSttus(searchVO);
	}

	/**
	 * 땅밀림 실태조사 임상 
	 * @param searchVO
	 * @return
	 * @description 땅밀림 실태조사 임상 
	 * @throws Exception
	 */
	@Override
	public List<EgovMap> selectLcpFrstfrvalSttus(LssLcpSvyComptVO searchVO) throws Exception {
		return sttusManageDAO.selectLcpFrstfrvalSttus(searchVO);
	}

	/**
	 * 땅밀림 실태조사 징후여부 
	 * @param searchVO
	 * @return
	 * @description 땅밀림 실태조사 징후여부 
	 * @throws Exception
	 */
	@Override
	public List<EgovMap> selectLcpDirsymptmSttus(LssLcpSvyComptVO searchVO) throws Exception {
		return sttusManageDAO.selectLcpDirsymptmSttus(searchVO);
	}

	/**
	 * @param searchVO
	 * @return
	 * @description 취약지역 실태조사 판정등급 현황
	 * @throws Exception
	 */
	@Override
	public List<EgovMap> selectWkaJdgmntgradSttus(LssWkaSvyComptVO searchVO) throws Exception {
		return sttusManageDAO.selectWkaJdgmntgradSttus(searchVO);
	}

	/**
	 * @param searchVO
	 * @return
	 * @description 취약지역 실태조사 보호시설 현황
	 * @throws Exception
	 */
	@Override
	public List<EgovMap> selectWkaSafefctSttus(LssWkaSvyComptVO searchVO) throws Exception {
		return sttusManageDAO.selectWkaSafefctSttus(searchVO);
	}

	/**
	 * @param searchVO
	 * @return
	 * @description 취약지역 실태조사 인가/호수 현황
	 * @throws Exception
	 */
	@Override
	public List<EgovMap> selectWkaHouseLakeSttus(LssWkaSvyComptVO searchVO) throws Exception {
		return sttusManageDAO.selectWkaHouseLakeSttus(searchVO);
	}

	/**
	 * @param searchVO
	 * @return
	 * @description 취약지역 실태조사 상부주요시설
	 * @throws Exception
	 */
	@Override
	public List<EgovMap> selectWkaHighmainfctSttus(LssWkaSvyComptVO searchVO) throws Exception {
		return sttusManageDAO.selectWkaHighmainfctSttus(searchVO);
	}

	/**
	 * @param searchVO
	 * @return
	 * @description 취약지역 실태조사 하부주요시설
	 * @throws Exception
	 */
	@Override
	public List<EgovMap> selectWkaLowmainfctSttus(LssWkaSvyComptVO searchVO) throws Exception {
		return sttusManageDAO.selectWkaLowmainfctSttus(searchVO);
	}

	/**
	 * @param searchVO
	 * @return
	 * @description 취약지역 실태조사 임상
	 * @throws Exception
	 */
	@Override
	public List<EgovMap> selectWkaFrtpSttus(LssWkaSvyComptVO searchVO) throws Exception {
		return sttusManageDAO.selectWkaFrtpSttus(searchVO);
	}

	/**
	 * @param searchVO
	 * @return
	 * @description 취약지역 실태조사 임분밀도
	 * @throws Exception
	 */
	@Override
	public List<EgovMap> selectWkaStddnstSttus(LssWkaSvyComptVO searchVO) throws Exception {
		return sttusManageDAO.selectWkaStddnstSttus(searchVO);
	}

	/**
	 * @param searchVO
	 * @return
	 * @description 취약지역 실태조사 임분경급
	 * @throws Exception
	 */
	@Override
	public List<EgovMap> selectWkaStddmclsSttus(LssWkaSvyComptVO searchVO) throws Exception {
		return sttusManageDAO.selectWkaStddmclsSttus(searchVO);
	}

	/**
	 * @param searchVO
	 * @return
	 * @description 취약지역 실태조사  사업 가능여부
	 * @throws Exception
	 */
	@Override
	public List<EgovMap> selectWkaBsposblatSttus(LssWkaSvyComptVO searchVO) throws Exception {
		return sttusManageDAO.selectWkaBsposblatSttus(searchVO);
	}

	/**
	 * @param searchVO
	 * @return
	 * @description 취약지역 실태조사 대책방안
	 * @throws Exception
	 */
	@Override
	public List<EgovMap> selectWkaCntrplnmethodSttus(LssWkaSvyComptVO searchVO) throws Exception {
		return sttusManageDAO.selectWkaCntrplnmethodSttus(searchVO);
	}

	/**
	 * @param searchVO
	 * @return
	 * @description 취약지역 해제조사 유역현황
	 * @throws Exception
	 */
	@Override
	public List<EgovMap> selectCnlDgrsttusSttus(LssCnlSvyComptVO searchVO) throws Exception {
		return sttusManageDAO.selectCnlDgrsttusSttus(searchVO);
	}

	/**
	 * @param searchVO
	 * @return
	 * @description 취약지역 해제조사 적용공법
	 * @throws Exception
	 */
	@Override
	public List<EgovMap> selectCnlApplcegnermhdSttus(LssCnlSvyComptVO searchVO) throws Exception {
		return sttusManageDAO.selectCnlApplcegnermhdSttus(searchVO);
	}

	/**
	 * @param searchVO
	 * @return
	 * @description 취약지역 해제조사 산사태등급
	 * @throws Exception
	 */		
	@Override
	public List<EgovMap> selectCnlLndsldgrdeSttus(LssCnlSvyComptVO searchVO) throws Exception {
		return sttusManageDAO.selectCnlLndsldgrdeSttus(searchVO);
	}

	/**
	 * @param searchVO
	 * @return
	 * @description 취약지역 해제조사 평균경사
	 * @throws Exception
	 */		
	@Override
	public List<EgovMap> selectCnlSlopeavgSttus(LssCnlSvyComptVO searchVO) throws Exception {
		return sttusManageDAO.selectCnlSlopeavgSttus(searchVO);
	}

	/**
	 * @param searchVO
	 * @return
	 * @description 취약지역 해제조사 임상도
	 * @throws Exception
	 */		
	@Override
	public List<EgovMap> selectCnlFrtptypeSttus(LssCnlSvyComptVO searchVO) throws Exception {
		return sttusManageDAO.selectCnlFrtptypeSttus(searchVO);
	}

	/**
	 * @param searchVO
	 * @return
	 * @description 취약지역 해제조사 경급도
	 * @throws Exception
	 */		
	@Override
	public List<EgovMap> selectCnlDmclstypeSttus(LssCnlSvyComptVO searchVO) throws Exception {
		return sttusManageDAO.selectCnlDmclstypeSttus(searchVO);
	}
	
	/**
	 * @param searchVO
	 * @return
	 * @description 임도 타당성평가 유형별 현황
	 * @throws Exception
	 */		
	@Override
	public List<EgovMap> selectFrdTypeSttus(VytFrdSvyComptVO searchVO) throws Exception {
		return sttusManageDAO.selectFrdTypeSttus(searchVO);
	}
	
	/**
	 * @param searchVO
	 * @return
	 * @description 임도 타당성평가 조사 목록 조회
	 * @throws Exception
	 */
	@Override
	public List<EgovMap> selectFrdSvyItems(VytFrdSvyComptVO searchVO) throws Exception{
		return sttusManageDAO.selectFrdSvyItems(searchVO);
	}
	
	/**
	 * @param searchVO
	 * @return
	 * @description 임도 타당성평가 관할별 건수조회
	 */
	@Override
	public List<EgovMap> selectFrdRegionSttus(VytFrdSvyComptVO searchVO) throws Exception {
		return sttusManageDAO.selectFrdRegionSttus(searchVO);
	}
	
	/**
	 * @param searchVO
	 * @return
	 * @description 임도 타당성평가 행정구역별 건수조회
	 */
	@Override
	public List<EgovMap> selectFrdAdministSttus(VytFrdSvyComptVO searchVO) throws Exception {
		return sttusManageDAO.selectFrdAdministSttus(searchVO);
	}
	
	/**
	 * @param sttusManageDAO
	 * @return
	 * @description 계측장비 연도별 건수 조회
	 */
	@Override
	public List<EgovMap> selectMseYearSttus(FckMseComptVO searchVO) throws Exception {
		return sttusManageDAO.selectMseYearSttus(searchVO);
	}
	
	/**
	 * @param sttusManageDAO
	 * @return
	 * @description 계측장비 시도별 와이어신축계 장비수 조회
	 */
	@Override
	public List<EgovMap> selectMseWireAdministSttus(FckMseComptVO searchVO) throws Exception {
		return sttusManageDAO.selectMseWireAdministSttus(searchVO);
	}
	
	/**
	 * @param sttusManageDAO
	 * @return
	 * @description 계측장비 시도별 지중경사계 장비수 조회
	 */
	@Override
	public List<EgovMap> selectMseLicmeterAdministSttus(FckMseComptVO searchVO) throws Exception {
		return sttusManageDAO.selectMseLicmeterAdministSttus(searchVO);
	}
	
	/**
	 * @param sttusManageDAO
	 * @return
	 * @description 계측장비 시도별 지하수위계 장비수 조회
	 */
	@Override
	public List<EgovMap> selectMseGwGaugeAdministSttus(FckMseComptVO searchVO) throws Exception {
		return sttusManageDAO.selectMseGwGaugeAdministSttus(searchVO);
	}
	
	/**
	 * @param sttusManageDAO
	 * @return
	 * @description 계측장비 시도별 강우계 장비수 조회
	 */
	@Override
	public List<EgovMap> selectMseRainGaugeAdministSttus(FckMseComptVO searchVO) throws Exception {
		return sttusManageDAO.selectMseRainGaugeAdministSttus(searchVO);
	}
	
	/**
	 * @param sttusManageDAO
	 * @return
	 * @description 계측장비 시도별 지표변위계 장비수 조회
	 */
	@Override
	public List<EgovMap> selectMseSurDpmAdministSttus(FckMseComptVO searchVO) throws Exception {
		return sttusManageDAO.selectMseSurDpmAdministSttus(searchVO);
	}
	
	/**
	 * @param sttusManageDAO
	 * @return
	 * @description 계측장비 시도별 GPS변위계 장비수 조회
	 */
	@Override
	public List<EgovMap> selectMseGpsGaugeAdministSttus(FckMseComptVO searchVO) throws Exception {
		return sttusManageDAO.selectMseGpsGaugeAdministSttus(searchVO);
	}
	
	/**
	 * @param sttusManageDAO
	 * @return
	 * @description 계측장비 시도별 게이트웨이 장비수 조회
	 */
	@Override
	public List<EgovMap> selectMseGatewayAdministSttus(FckMseComptVO searchVO) throws Exception {
		return sttusManageDAO.selectMseGatewayAdministSttus(searchVO);
	}
	
	/**
	 * @param sttusManageDAO
	 * @return
	 * @description 계측장비 시도별 노드 장비수 조회
	 */
	@Override
	public List<EgovMap> selectMseNodeAdministSttus(FckMseComptVO searchVO) throws Exception {
		return sttusManageDAO.selectMseNodeAdministSttus(searchVO);
	}	
	
	/**
	 * @param searchVO
	 * @return
	 * @description 정밀점검 조사 목록 조회
	 * @throws Exception
	 */
	@Override
	public List<EgovMap> selectPcsSvyItems(FckPcsComptVO searchVO) throws Exception{
		return sttusManageDAO.selectPcsSvyItems(searchVO);
	}
	
	/**
	 * @param searchVO
	 * @return
	 * @description 정밀점검 행정구역별 건수조회
	 */
	@Override
	public List<EgovMap> selectPcsAdministSttus(FckPcsComptVO searchVO) throws Exception {
		return sttusManageDAO.selectPcsAdministSttus(searchVO);
	}
}
