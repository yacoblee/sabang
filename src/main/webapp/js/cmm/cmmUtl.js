/*
 * 요소기술 스크립트  
 */

// 숫자체크
function isNumber(control, msg) {
	
	var val = control;
	var Num = "1234567890";
	for (i=0; i<val.length; i++) {
		if(Num.indexOf(val.substring(i,i+1))<0) {
			alert(msg+' 형식이 잘못되었습니다.');
			return false;
		}
	}
	return true;
}

// 날짜체크
function isDate(control, msg) {
	
	// '/'나 '-' 구분자 제거
	var val = getRemoveFormat(control);
	
	// 숫자, length 확인
	if (isNumber(val, msg) && val.length == 8) {
		var year = val.substring(0,4);
		var month = val.substring(4,6);
		var day = val.substring(6,8);
		
		// 유효날짜 확인
		if(checkDate(year,month,day,msg)){
			return true;
		} else {
			return false;
		}
	} else {
		alert(msg + " 유효하지 않은 년,월,일(YYYYMMDD)입니다. 다시 확인해 주세요!");
		return false;
	}
}

// 구분자 제거
function getRemoveFormat(val) {
	if(val.length == 10) {
		var arrDate = new Array(3);
		arrDate = val.split("/");
		if(arrDate.length != 3) {
			arrDate = val.split("-");
		}
		return arrDate[0] + arrDate[1] + arrDate[2];
	} else {
		return val;
	}	
}

// 유효날짜 확인
function checkDate(varCk1, varCk2, varCk3, msg) {
	if (varCk1>="0001" && varCk1<="9999" && varCk2>="01" && varCk2<="12") {
		febDays = "29";
		if ((parseInt(varCk1,10) % 4) == 0) {
			if ((parseInt(varCk1,10) % 100) == 0 && (parseInt(varCk1,10) % 400) != 0){
				febDays = "28";
			}
		}else{
			febDays = "28";
		}
		if (varCk2=="01" && varCk3>="01" && varCk3<="31") return true;
		if (varCk2=="02" && varCk3>="01" && varCk3<=febDays) return true;
		if (varCk2=="03" && varCk3>="01" && varCk3<="31") return true;
		if (varCk2=="04" && varCk3>="01" && varCk3<="30") return true;
		if (varCk2=="05" && varCk3>="01" && varCk3<="31") return true;
		if (varCk2=="06" && varCk3>="01" && varCk3<="30") return true;
		if (varCk2=="07" && varCk3>="01" && varCk3<="31") return true;
		if (varCk2=="08" && varCk3>="01" && varCk3<="31") return true;
		if (varCk2=="09" && varCk3>="01" && varCk3<="30") return true;
		if (varCk2=="10" && varCk3>="01" && varCk3<="31") return true;
		if (varCk2=="11" && varCk3>="01" && varCk3<="30") return true;
		if (varCk2=="12" && varCk3>="01" && varCk3<="31") return true;
	}
	alert(msg + " 유효하지 않은 년,월,일(YYYYMMDD)입니다. 다시 확인해 주세요!");
	return false;
}

/*
 * @author podo
 * @name isEmpty
 * @param
 * @return
 * @description 공백체크
 */
function isEmpty(args) {
    var result = true;

    if (!args || 0 === args.length) result = false;
    if (!args || (/^\s*$/).test(args)) result = false;
    if (!args || !args.trim()) result = false;

    return result;
}

/*
 * @author podo
 * @name hasSpc
 * @param
 * @return
 * @description 특수문자 체크
 */
function hasSpc(args) {
    var result = true;

    if (!("/[~!@#$%^&*()_+|<>?:{}]/").test(args)) result = false;

    return result;
}

/*
 * @author podo
 * @name checkCharType
 * @param
 * @return
 * @description 영문 숫자 체크
 */
function checkCharType(args) {
    var result = false;

    if ((/^[A-Za-z0-9+]*$/).test(args)) result = true;

    return result;

}
/*
 * @author podo
 * @name isValidate
 * @param
 * @return
 * @description 체크
 */
function isValidate(args) {
    var result = 0;

    if (args.length == 0) return;

    $.each(args, function(i, f) {
        if (!isEmpty(f)) result++;
    });

    return (result > 0) ? true : false;
}

//초를 시분초로 변경
function convertSsToMm(args){
	var hour = parseInt(args/3600);
	var min = parseInt((args%3600)/60);
	var sec = args%60;
	
	return (hour > 0 ? hour+"시간 " : "") + (min > 0 ? min+"분 " : "") + sec+"초";
}