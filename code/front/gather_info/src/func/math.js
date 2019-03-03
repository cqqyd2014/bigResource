var math = {

    rmbToBig(n) {

        var fraction = ['角', '分'];
        var digit = ['零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'];
        var unit = [
            ['元', '万', '亿'],
            ['', '拾', '佰', '仟']
        ];
        var head = n < 0 ? '欠' : '';
        n = Math.abs(n);

        var s = '';

        for (let i = 0; i < fraction.length; i++) {
            s += (digit[Math.floor(n * 10 * Math.pow(10, i)) % 10] + fraction[i]).replace(/零./, '');
        }
        s = s || '整';
        n = Math.floor(n);

        for (let i = 0; i < unit[0].length && n > 0; i++) {
            var p = '';
            for (var j = 0; j < unit[1].length && n > 0; j++) {
                p = digit[n % 10] + unit[1][j] + p;
                n = Math.floor(n / 10);
            }
            s = p.replace(/(零.)*零$/, '').replace(/^$/, '零') + unit[0][i] + s;
        }
        return head + s.replace(/(零.)*零元/, '元').replace(/(零.)+/g, '零').replace(/^整$/, '零元整');

    },
    /* 
 7  * formatMoney(s,type) 
 8  * 功能：金额按千位逗号分隔，负号用括号
 9  * 参数：s，需要格式化的金额数值. 
10  * 参数：type,判断格式化后的金额是否需要小数位. 
11  * 返回：返回格式化后的数值字符串. 
12  */
    moneyToThousand(s, type) {
        var result = s;
        if (s < 0)
            s = 0 - s;
        if (/[^0-9\.]/.test(s))
            return "0.00";
        if (s === null || s === "null" || s === "")
            return "0.00";
        if (type > 0)
            s = new Number(s).toFixed(type);
        s = s.toString().replace(/^(\d*)$/, "$1.");
        s = (s + "00").replace(/(\d*\.\d\d)\d*/, "$1");
        s = s.replace(".", ",");
        var re = /(\d)(\d{3},)/;
        while (re.test(s))
            s = s.replace(re, "$1,$2");
        s = s.replace(/,(\d\d)$/, ".$1");
        if (type === 0) {
            var a = s.split(".");
            if (a[1] === "00") {
                s = a[0];
            }
        }
        if (result < 0)
            result = "(" + s + ")";
        else
            result = s;
        return result;

    }

}

export default math;