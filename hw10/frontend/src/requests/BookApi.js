export const deleteApiData = async (id, action) => {
    await fetch(
        "/api/v1/books/" + id,
        {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json",
            },
        }
    );
    await getApiData(action);
};

export const getApiFilteredData = async (setData, authorId) => {
    let fetchUrl = "/api/v1/books";
    if (authorId !== null) {
        fetchUrl = fetchUrl.concat("?authorId=", authorId);
    }

    const response = await fetch(
        fetchUrl
    ).then((response) => response.json());
    setData(response);
};

const getApiData = async (setData) => {
    const response = await fetch(
        "/api/v1/books"
    ).then((response) => response.json());
    setData(response);
};