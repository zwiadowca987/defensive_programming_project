import Link from "next/link";

// TODO: download orders from the backend
const clientsList = [
  {
    id: 1,
    name: "Janek 1",
    address: "Jakiś 1",
    email: "jakiś 1",
    phone: 100,
  },
  {
    id: 2,
    name: "Janek 2",
    address: "Jakiś 2",
    email: "jakiś 2",
    phone: 200,
  },
];

export default function Clients() {
  return (
    <div className={"container"}>
      <div className={"text-center"}>
        <h1 className={"display-2"}>Klienci</h1>
      </div>
      <div className={"m-5 list-group"}>
        <Link className={"btn d-flex text-end"} href={"/clients/create/"}>
          Dodaj Nowego Klienta
        </Link>

        {clientsList.map((client) => (
          <div className={"list-group-item"} key={client.id}>
            <p className={"mb-1"}>{client.name}</p>
            <p className={"mb-1"}>{client.address} PLN</p>
            <p className={"mb-1"}>{client.email}</p>
            <p className={"mb-1"}>{client.phone}</p>

            {/* TODO: Tutaj chyba trochę inaczej to ale to najpierw musi być pobieranie */}
            <Link className={"btn"} href={`/clients/edit/${client.id}`}>
              <i className={"bi bi-pencil-square"}></i> Edytuj
            </Link>
            <Link className={"btn"} href={`/clients/delete/${client.id}`}>
              <i className={"bi bi-trash"}></i> Usuń
            </Link>
          </div>
        ))}
      </div>
    </div>
  );
}
