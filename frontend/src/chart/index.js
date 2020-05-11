function generateChartData(_arr_columns){
  let arr_columns = _arr_columns
  arr_columns.forEach(columnNm => {
    convertObjArrToSeries(columnNm)
  })
  return arr_columns
}

function convertObjArrToSeries(columnNm){
  let target = this[columnNm]
  this.apiData.forEach(obj => {
    let data = obj[columnNm] === null ? undefined : obj[columnNm]
    target.push(data)
  })
}

export default generateChartData;
