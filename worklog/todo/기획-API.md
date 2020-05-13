# API 기획
# 1. 일별 데이터
## 일별 데이터의 기준 
일별 데이터로 취급되는 항목은 아래와 같다.
- KOSPI
- S&P 500
- 환율(원/달러)
- 환율(원/엔화)

각 일별 데이터 조회시
- 해당 년/월 에서의 최대값
- 해당 년/월 에서의 최소값
- 해당 년/월 에서의 평균값
을 조회해온다.

## 일별 데이터 조회 API
일별 데이터 각각의 조회 API URL
- /api/trending/index/{category}?startDate=YYYYMMDD&endDate=YYYYMMDD
    - startDate, endDate 는 선택적인 파라미터이다.
    - startDate, endDate 가 없을 경우 1960년도 부터 현재까지의 데이터를 인출해온다.

일별데이터 모두 조회

