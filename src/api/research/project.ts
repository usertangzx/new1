import request from '/@/utils/request';

export function listProject(query: any) {
	return request({
		url: '/ProjectInfo/list',
		method: 'get',
		params: query,
	});
}

export function addProject(data: any) {
	return request({
		url: '/ProjectInfo/add',
		method: 'post',
		data,
	});
}

export function updateProject(data: any) {
	return request({
		url: '/ProjectInfo/update',
		method: 'post',
		data,
	});
}

export function delProject(ids: string) {
	return request({
		url: '/ProjectInfo/delete',
		method: 'post',
		data: ids,
	});
}
