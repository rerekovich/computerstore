async function showProductList() {
    document.getElementById('content').innerHTML = `
        <h3>Список товарів</h3>
        <label>Оберіть категорію:</label>
        <select id="filterCategory" class="form-control mb-2" onchange="loadProductListByCategory()">
            <option value="">-- Всі категорії --</option>
            ${categories.map(cat => `<option value="${cat}">${cat}</option>`).join('')}
        </select>
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>Категорія</th>
                    <th>Виробник</th>
                    <th>Найменування моделі</th>
                    <th>Ціна покупки</th>
                    <th>Ціна продажу</th>
                    <th>Кількість на складі</th>
                </tr>
            </thead>
            <tbody id="productList"></tbody>
        </table>
        <button class="btn btn-secondary" onclick="goBack()">Назад</button>
    `;
    loadProductListByCategory();
}
async function loadProductListByCategory() {
    try {
        const category = document.getElementById('filterCategory').value;
        const res = await api.get('/products');
        const filtered = category ? res.data.filter(p => p.category === category) : res.data;

        document.getElementById('productList').innerHTML = filtered.map(p => `
            <tr>
                <td>${p.category}</td>
                <td>${p.manufacturer}</td>
                <td>${p.modelName}</td>
                <td>${p.purchasePrice}₴</td>
                <td>${p.salePrice}₴</td>
                <td>${p.quantityInStock} шт.</td>
            </tr>
        `).join('');
    } catch (e) {
        alert("Помилка при завантаженні списку: " + (e.response?.data?.message || e.message));
    }
}
