# 📑 Nexacro + XUP 기반 증빙·결재 시스템 (Toy Project)

> 본 프로젝트는 **Nexacro + XUP + Oracle + Java(Spring)** 를 활용하여 구현한 **증빙·결재 시스템** 토이 프로젝트입니다.  
> 실제 운영 목적이 아닌 **학습·포트폴리오** 제작을 위해 설계되었으며, 일부 기능은 단순화 또는 데모 수준으로 구성되어 있습니다.  

---

## 🛠 주요 기능
- **증빙 관리**
  - 증빙 문서 등록 / 조회 / 삭제
  - 첨부파일 업로드 및 PDF 변환 (LibreOffice 기반)
- **결재 프로세스**
  - 결재선 지정 (조직도 Tree 기반)
  - 결재 상신 / 진행 / 완료 흐름 관리
  - 결재 상태 코드(PENDING, PROGRESS, COMPLETED) 관리
- **UI 구성**
  - Nexacro Grid + Tree 기능 활용
  - 팝업(Modal) 호출 및 데이터 전달
- **XUP 연동**
  - Dataset 기반 SQL 매핑 (조회/등록/수정)
  - 다중 Dataset 트랜잭션 처리  

---

## 🚀 실행 환경
- **Frontend**: Nexacro Studio (Grid, Dataset, Transaction)  
- **Middleware**: X-UP (XML 매퍼 기반 SQL 처리)  
- **Backend**: Java(Spring), Tomcat 9  
- **Database**: Oracle  

---

## 📂 폴더 구조
```
workspace/
├── .metadata/                # Nexacro Studio 내부 메타데이터 (버전/환경설정)  
├── .xup/                     # XUP 관련 캐시/설정 파일  
├── Compile/                  # 컴파일된 결과물 (Nexacro Form, XUP 매퍼 XML)  
├── NEXACRO_APPROVAL_DEMO/    # Nexacro 프로젝트 소스 (Form, Script, Dataset 등)  
├── Servers/                  # 로컬 서버 관련 설정 (Tomcat 등)  
└── XUP_DEMO/                 # XUP 매퍼(XML) 및 SQL 연동 파일  
```
---

## 🖼 화면 예시
| 증빙 조회 | 파일 업로드 | 결재선 지정 | 결재 현황 |
|-----------|-------------|--------------|------------|
| ![](https://velog.velcdn.com/images/binhk/post/e280f95c-4add-4e07-b38f-4ca9124414dc/image.PNG) | ![](https://velog.velcdn.com/images/binhk/post/f3f043e8-fe15-461e-abba-b0a0d6ae430e/image.PNG) | ![](https://velog.velcdn.com/images/binhk/post/cf510376-3037-4b69-971c-c305995151c1/image.PNG) | ![](https://velog.velcdn.com/images/binhk/post/92b38d7b-68bd-46a2-a8b8-45b879f0239a/image.PNG) |

---

## 📖 참고
- 블로그 시리즈: [Velog - 좌충우돌 넥사크로·XUP 제작기](https://velog.io/@binhk)  
- Nexacro 공식 문서: [https://docs.tobesoft.com/nexacro_n_ko](https://docs.tobesoft.com/nexacro_n_ko)  
- PlayNexacro: [https://www.playnexacro.com](https://www.playnexacro.com)  

---

## 📜 라이선스
본 프로젝트는 **학습 및 참고용**으로 제공되며,  
상업적 사용은 권장하지 않습니다.  
