function showAddForm() {
        document.getElementById('content').innerHTML = `
            <h3>Додати товар</h3>
            <form onsubmit="event.preventDefault(); addProduct();">
                <label>Категорія:</label>
                <select id="addCategory" name="category" class="form-control mb-2">
                    ${categories.map(cat => `<option value="${cat}">${cat}</option>`).join('')}
                </select>
                <label>Виробник:</label>
                <input id="manufacturer" name="manufacturer" class="form-control mb-2" required>
                <label>Модель:</label>
                <input id="modelName" name="modelName" class="form-control mb-2" required>
                <label>Ціна закупки:</label>
                <input id="purchasePrice" name="purchasePrice" type="number" step="0.01" class="form-control mb-2" required>
                <label>Ціна продажу:</label>
                <input id="salePrice" name="salePrice" type="number" step="0.01" class="form-control mb-2" required>
                <label>Кількість:</label>
                <input id="quantity" name="quantity" type="number" class="form-control mb-2" required>
                <button class="btn btn-success">Додати</button>
                <button class="btn btn-secondary" type="button" onclick="goBack()">Назад</button>
            </form>
        `;
}
    async function addProduct() {
        try {
            const product = {
                category: document.getElementById('addCategory').value,
                manufacturer: document.getElementById('manufacturer').value,
                modelName: document.getElementById('modelName').value,
                purchasePrice: parseFloat(document.getElementById('purchasePrice').value),
                salePrice: parseFloat(document.getElementById('salePrice').value),
                quantityInStock: parseInt(document.getElementById('quantity').value)
            };
            await api.post('/products', product);
            alert("Товар додано");
            goBack();
        } catch (e) {
            alert("Помилка при додаванні товару: " + (e.response?.data?.message || e.message));
        }
    }

