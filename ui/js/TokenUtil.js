// 判断浏览器是否支持 localStorage
function isSupport() {
	if (!window.localStorage) {
		console.error("浏览器不支持localStorage")
		return false
	}
	return true
}

/**
 * 添加
 */
function addToken(token) {
	if (!isSupport) 
		return false
	let storage = window.localStorage
	storage.setItem(TOKEN, token)
	return true
}

/**
 * 删除
 */
function deleteToken() {
	if (!isSupport) 
		return false
	let storage = window.localStorage
	storage.removeItem(TOKEN)
	return true
}

/**
 * 修改
 */
function updateToken(token) {
	if (!isSupport) 
		return false
	let storage = window.localStorage
	storage.setItem(TOKEN, token)
	return true
}

/**
 * 查询
 */
function getToken() {
	if (!isSupport) 
		return;
	let storage = window.localStorage
	return storage.getItem(TOKEN)
}